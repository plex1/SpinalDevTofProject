/*
 * queue
 *
 *  Created on: Nov 19, 2018
 *      Author: spinaldev
 */

#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <stdlib.h>

char queue[50] = "";
char* queue_tail = &queue[0];
char* queue_head = &queue[0];

#define END_OF_QUEUE (&queue[sizeof(queue)/sizeof(*queue)]) // The first element _after_ the buffer.

#define QUEUE_NOT_FULL   0
#define QUEUE_IS_FULL    1

uint8_t queueState;
uint32_t queueLength = 0;

void queue_push(const char l_item)
{
    // 0) Addition is always modulus buffer size (50)
    // 1) head points to the next empty location which can store an incoming byte
    // 2) tail points to the next location from where a byte may be read
    // 3) Queue is empty iff tail == head && queueState == QUEUE_NOT_FULL
    // 4) Queue is full iff tail == head && queueState == QUEUE_IS_FULL

    if ( queueState == QUEUE_NOT_FULL ) {

        // Store item to the current head:
        *queue_head = l_item;

        // Advance head by one:
        queue_head++;

        if ( queue_head == END_OF_QUEUE ) {
            // head passed the end of buffer -> continue at the start:
            queue_head = &queue[0];
        }

        // If head meets tail after appending the new element, the buffer is now full.
        if ( queue_head == queue_tail ) {
            queueState = QUEUE_IS_FULL;
        }
        queueLength++;

    } else {
        // Buffer overflow! - Options: Ignore new data, overwrite old data, wait until not full, signal an error somehow.
    }

}

char queue_pop()
{

    if ( (queue_tail == queue_head) && (queueState == QUEUE_NOT_FULL) ) {
        // Queue is empty. "Buffer underflow." - Options: Return dummy value, wait until data available, signal an error somehow.
        return '\0';
    } else {
        const char result = *queue_tail;
        queue_tail++;

        if ( queue_tail == END_OF_QUEUE ) {
            // tail passed the end of buffer -> continue at the start:
            queue_tail = &queue[0];
        }

        // We just removed an element from the queue, so the queue cannot be full now even if it was full a moment ago.
        queueState = QUEUE_NOT_FULL;
        queueLength--;

        return result;
    }

}

