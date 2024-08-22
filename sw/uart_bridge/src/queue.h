/*
 * queue
 *
 *  Created on: Nov 19, 2018
 *      Author: spinaldev
 */

#ifndef QUEUE_
#define QUEUE_

	void queue_push(const char l_item);
	char queue_pop();

	extern uint8_t queueState;
	extern uint32_t queueLength;

#endif /* QUEUE_ */
