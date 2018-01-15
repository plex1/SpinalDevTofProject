SUBDIRS := $(wildcard */.)

# Filter out the common folder
SUBDIRS := $(filter-out common/.,$(SUBDIRS))

all:
	for dir in $(SUBDIRS); do \
		(cd $$dir; ${MAKE} all); \
	done

clean:
	for dir in $(SUBDIRS); do \
		(cd $$dir; ${MAKE} clean); \
	done

.PHONY: all $(SUBDIRS)
