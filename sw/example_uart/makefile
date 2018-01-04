PROJ_NAME=uart
DEBUG=yes
BENCH=no
MULDIV=no


SRCS = 	$(wildcard src/*.c) \
	$(wildcard src/*.cpp)  \
	$(wildcard src/*.S)


LDSCRIPT = ../common/script/linker.ld
INC += -I../common/lib/
INC += -I../common/lib/soc/

include ../common/script/gcc.mk
include ../common/script/subproject.mk
