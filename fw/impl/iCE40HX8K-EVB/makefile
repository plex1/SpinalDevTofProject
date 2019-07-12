

VERILOG = ../../hdl/MuraxCustom.v toplevel.v toplevel_pll.v ../../hdl/mergeRTL.v

generate :
	(cd ../../hdl; sbt "run-main soc.MuraxCustom")

../../hdl/MuraxCustom.v :
	(cd ../../hdl; sbt "run-main soc.MuraxCustom")

bin/toplevel.json : ${VERILOG}
	mkdir -p bin
	rm -f Murax.v*.bin
	cp ../../hdl/MuraxCustom.v*.bin . | true
	yosys -v3 -p "synth_ice40 -top toplevel -json bin/toplevel.json" ${VERILOG}
	#yosys -v3 -p "synth_ice40 -top toplevel -blif bin/toplevel.blif" ${VERILOG}

#bin/toplevel.asc : toplevel.pcf bin/toplevel.blif
#	arachne-pnr -p toplevel.pcf -d 8k --max-passes 600 -P ct256 bin/toplevel.blif -o bin/toplevel.asc

bin/toplevel.asc: toplevel.pcf bin/toplevel.json
	nextpnr-ice40 --hx8k --json bin/toplevel.json --pcf toplevel.pcf --asc bin/toplevel.asc --pre-place floorplan.py

bin/toplevel.bin : bin/toplevel.asc
	icepack bin/toplevel.asc bin/toplevel.bin

compile : bin/toplevel.bin

time: bin/toplevel.bin
	icetime -timd hx8k bin/toplevel.asc -c 40

prog : bin/toplevel.bin
	iceprogduino bin/toplevel.bin

sudo-prog : bin/toplevel.bin
	sudo iceprogduino bin/toplevel.bin

clean :
	rm -rf bin
	rm -f Murax.v*.bin
