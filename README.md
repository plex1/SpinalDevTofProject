# SpinalDevTofProject

This repository includes the code for a FPGA on the [ioda_lidar](https://github.com/plex1/ioda_lidar) project. 
- Logicware
  - RiscV (VexRisc) softcore CPU
  - Time-of-flight 
     - start pulse generation
     - time-to-digital converter based propagation delay of look-up-tables (LUTs)
     - histogram of deteccted delays
- Embedded software (on RiscV)
   - Protocol (Gepin) which forwards read and write request from UART to the internal bus (mainly to the  time-of-flight logic)


## Compilation and testing

1. Startup [SpinalDev](https://github.com/plex1/SpinalDev) docker environment.
2. Within docker, clone this repository into the user folder:
```
cd /home/spinaldev/projects/user
git clone https://github.com/plex1/SpinalDevTofProject.git   
```
3. Run project specific actions
```
./project.bash run -n SpinalDevTofProject -t soc -a fwcompile  # compile hdl and generate verilog
./project.bash run -n SpinalDevTofProject -t soc -a swbuild    # build sw and and genrate binary
./project.bash run -n SpinalDevTofProject -t soc -a fwtest     # run testbench (needs sw binary)
```


## Remarks

This repos was adopted from [SpinalDevTemplateSoc](https://github.com/plex1/SpinalDevTemplateSoc)
Tested on [FPGA board (Olimex)](https://www.olimex.com/Products/FPGA/iCE40/iCE40HX8K-EVB/open-source-hardware)
Programming and debugging of FPGA/SW has been done according to [raspice40](https://github.com/plex1/raspice40)

