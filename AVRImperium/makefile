# MCU name
MCU = at90usb1286
# Target architecture (see library "Board Types" documentation).
ARCH = AVR8
# Target board
BOARD = TEENSY2
# Processor frequency.
F_CPU = 16000000
# Target file name (without extension).
TARGET = AVRImperium






# Input clock frequency.
F_USB = $(F_CPU)
# Output format. (can be srec, ihex, binary)
FORMAT = ihex
# Optimization level, can be [0, 1, 2, 3, s].
OPT = s
# Debugging format.
DEBUG = dwarf-2
# Compiler flag to set the C Standard level.
CSTANDARD = -std=c99
# Object files directory
BUILD_DIR = ./build










# Place -D or -U options here for C sources
CDEFS  = -DF_CPU=$(F_CPU)UL

# Place -D or -U options here for ASM sources
ADEFS  = -DF_CPU=$(F_CPU)

# Place -D or -U options here for C++ sources
CPPDEFS  = -DF_CPU=$(F_CPU)UL
#CPPDEFS += -D__STDC_LIMIT_MACROS
#CPPDEFS += -D__STDC_CONSTANT_MACROS









# List C source files here. (C dependencies are automatically generated.)
SRC = main.c

# List C++ source files here. (C dependencies are automatically generated.)
CPPSRC =

# List Assembler source files here.
ASRC =

# List any extra directories to look for include files here.
EXTRAINCDIRS = 


# Path to the LUFA library
LUFA_PATH = .
include usbserial/makefile

include util/makefile

include imperium/makefile

include objects/makefile



#compiler options
include makefile_compiler_options





















#---------------- Programming Options (avrdude) ----------------

# Programming hardware
# Type: avrdude -c ?
# to get a full listing.
#
AVRDUDE_PROGRAMMER = jtagmkII

# com1 = serial port. Use lpt1 to connect to parallel port.
AVRDUDE_PORT = usb

AVRDUDE_WRITE_FLASH = -U flash:w:$(TARGET).hex
#AVRDUDE_WRITE_EEPROM = -U eeprom:w:$(TARGET).eep

# Uncomment the following if you want avrdude's erase cycle counter.
# Note that this counter needs to be initialized first using -Yn,
# see avrdude manual.
#AVRDUDE_ERASE_COUNTER = -y

# Uncomment the following if you do /not/ wish a verification to be
# performed after programming the device.
#AVRDUDE_NO_VERIFY = -V

# Increase verbosity level.  Please use this when submitting bug
# reports about avrdude. See <http://savannah.nongnu.org/projects/avrdude>
# to submit bug reports.
#AVRDUDE_VERBOSE = -v -v

AVRDUDE_FLAGS = -p $(MCU) -P $(AVRDUDE_PORT) -c $(AVRDUDE_PROGRAMMER)
AVRDUDE_FLAGS += $(AVRDUDE_NO_VERIFY)
AVRDUDE_FLAGS += $(AVRDUDE_VERBOSE)
AVRDUDE_FLAGS += $(AVRDUDE_ERASE_COUNTER)









# Define programs and commands.
SHELL = sh
CC = avr-gcc
OBJCOPY = avr-objcopy
OBJDUMP = avr-objdump
SIZE = avr-size
AR = avr-ar rcs
NM = avr-nm
AVRDUDE = avrdude
REMOVE = rm -f
REMOVEDIR = rm -rf
COPY = cp
WINSHELL = cmd




# Define Messages
# English
MSG_ERRORS_NONE = Errors: none
MSG_SIZE_BEFORE = Size before:
MSG_SIZE_AFTER = Size after:
MSG_COFF = Converting to AVR COFF:
MSG_EXTENDED_COFF = Converting to AVR Extended COFF:
MSG_FLASH = Creating load file for Flash:
MSG_EEPROM = Creating load file for EEPROM:
MSG_EXTENDED_LISTING = Creating Extended Listing:
MSG_SYMBOL_TABLE = Creating Symbol Table:
MSG_LINKING = Linking:
MSG_COMPILING = Compiling C:
MSG_COMPILING_CPP = Compiling C++:
MSG_ASSEMBLING = Assembling:
MSG_CLEANING = Cleaning project:
MSG_CREATING_LIBRARY = Creating library:





# Define all object files.
OBJ = $(SRC:%.c=$(BUILD_DIR)/%.o) $(CPPSRC:%.cpp=$(BUILD_DIR)/%.o) $(ASRC:%.S=$(BUILD_DIR)/%.o)

# Define all listing files.
LST = $(SRC:%.c=$(BUILD_DIR)/%.lst) $(CPPSRC:%.cpp=$(BUILD_DIR)/%.lst) $(ASRC:%.S=$(BUILD_DIR)/%.lst)


# Compiler flags to generate dependency files.
GENDEPFLAGS = -MMD -MP -MF .dep/$(@F).d


# Combine all necessary flags and optional flags.
# Add target processor to flags.
ALL_CFLAGS = -mmcu=$(MCU) -I. $(CFLAGS) $(GENDEPFLAGS)
ALL_CPPFLAGS = -mmcu=$(MCU) -I. -x c++ $(CPPFLAGS) $(GENDEPFLAGS)
ALL_ASFLAGS = -mmcu=$(MCU) -I. -x assembler-with-cpp $(ASFLAGS)








# Default target.
all: gccversion sizebefore build sizeafter


include makefile_build












# Target: clean project.
clean:
	@echo
	@echo $(MSG_CLEANING)
	$(REMOVE) -r $(BUILD_DIR)
	$(REMOVE) $(TARGET).hex
	$(REMOVE) $(TARGET).eep
	$(REMOVE) $(TARGET).cof
	$(REMOVE) $(TARGET).elf
	$(REMOVE) $(TARGET).map
	$(REMOVE) $(TARGET).sym
	$(REMOVE) $(TARGET).lss
	$(REMOVE) $(SRC:%.c=$(BUILD_DIR)/%.o) $(CPPSRC:%.cpp=$(BUILD_DIR)/%.o) $(ASRC:%.S=$(BUILD_DIR)/%.o)
	$(REMOVE) $(SRC:%.c=$(BUILD_DIR)/%.lst) $(CPPSRC:%.cpp=$(BUILD_DIR)/%.lst) $(ASRC:%.S=$(BUILD_DIR)/%.lst)
	$(REMOVE) $(SRC:.c=.s)
	$(REMOVE) $(SRC:.c=.d)
	$(REMOVE) $(SRC:.c=.i)
	$(REMOVEDIR) .dep


# Create object files directory
$(shell mkdir $(BUILD_DIR) 2>/dev/null)


# Include the dependency files.
-include $(shell mkdir .dep 2>/dev/null) $(wildcard .dep/*)


# Listing of phony targets.
.PHONY : all finish end sizebefore sizeafter gccversion \
build elf hex eep lss sym clean program
