REMOTE_HOST = 192.168.1.105

all: sw logic_compile progfile configfile_copy configfile_prog

sw:
	$(MAKE) -C sw

logic_compile:
	$(MAKE) compile -C fw/hdl

progfile:
	$(MAKE) -C fw/impl/iCE40HX8K-EVB

intellij:
	intellij fw/hdl &

eclipse:
	eclipse &

openocd:
	cd /opt/openocd_riscv/; src/openocd -f tcl/interface/jtag_tcp.cfg -c "set MURAX_CPU0_YAML /home/spinaldev/projects/user/tof/fw/hdl/cpu0.yaml" -f tcl/target/murax.cfg

gtkwave:
	gtkwave fw/hdl/simWorkspace/MuraxCustom/test.vcd &

configfile_copy:
	scp fw/impl/iCE40HX8K-EVB/bin/toplevel.bin pi@$(REMOTE_HOST):~

configfile_prog:
	ssh  pi@$(REMOTE_HOST) 'bash -c "cd raspice40 && sudo ./program.sh ../toplevel.bin"'

