
# LUFA library compile-time options and predefined tokens
LUFA_OPTS  = -D USB_DEVICE_ONLY
LUFA_OPTS += -D FIXED_CONTROL_ENDPOINT_SIZE=8
LUFA_OPTS += -D FIXED_NUM_CONFIGURATIONS=1
LUFA_OPTS += -D USE_FLASH_DESCRIPTORS
LUFA_OPTS += -D USE_STATIC_OPTIONS="(USB_DEVICE_OPT_FULLSPEED | USB_OPT_REG_ENABLED | USB_OPT_AUTO_PLL)"


# Create the LUFA source path variables by including the LUFA root makefile
include $(LUFA_PATH)/LUFA/makefile

# List C source files here. (C dependencies are automatically generated.)
SRC += usbserial/VirtualSerial.c                                                \
	  usbserial/Descriptors.c                                               \
	  $(LUFA_SRC_USB)                                             \
	  $(LUFA_SRC_USBCLASS)
	  

# List any extra directories to look for include files here.
EXTRAINCDIRS += $(LUFA_PATH)/

CDEFS += $(LUFA_OPTS)
CDEFS += -DBOARD=BOARD_$(BOARD) -DARCH=ARCH_$(ARCH)
CDEFS += -DF_USB=$(F_USB)UL

ADEFS += $(LUFA_OPTS)
ADEFS += -DBOARD=BOARD_$(BOARD)
ADEFS += -DF_USB=$(F_USB)UL

CPPDEFS += $(LUFA_OPTS)
CPPDEFS += -DBOARD=BOARD_$(BOARD)
CPPDEFS += -DF_USB=$(F_USB)UL

