

VERILOG = ../../hdl/MuraxCustom.v toplevel.v
CONSTRAINTS = toplevel.pcf

generate :
	(cd ../../hdl; sbt "run-main soc.MuraxCustom")

../../hdl/MuraxCustom.v :
	(cd ../../hdl; sbt "run-main soc.MuraxCustom")

bin/toplevel.blif : ${VERILOG}
	mkdir -p bin
	yosys -v3 -p "synth_ice40 -top toplevel -blif bin/toplevel.blif" ${VERILOG}

bin/toplevel.asc :  ${CONSTRAINTS} bin/toplevel.blif
	arachne-pnr -p ${CONSTRAINTS} -d 8k --max-passes 600 -P ct256 bin/toplevel.blif -o bin/toplevel.asc

bin/toplevel.bin : bin/toplevel.asc
	icepack bin/toplevel.asc bin/toplevel.bin

compile : bin/toplevel.bin

time: bin/toplevel.bin
	icetime -tmd hx8k bin/toplevel.asc

prog : bin/toplevel.bin
	iceprogduino bin/toplevel.bin

sudo-prog : bin/toplevel.bin
	sudo iceprogduino bin/toplevel.bin

clean :
	rm -rf bin
