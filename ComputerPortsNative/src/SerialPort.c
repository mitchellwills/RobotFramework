/*
 * SerialPort.c
 *
 *  Created on: Mar 27, 2012
 *      Author: Mitchell
 */

//Based on http://www.robbayer.com/files/serial-win.pdf
//Also see http://msdn.microsoft.com/en-us/library/ms810467.aspx
//http://www.ftdichip.com/Support/Documents/AppNotes/AN232B-04_DataLatencyFlow.pdf
//http://electronics.stackexchange.com/questions/28434/sensor-polling-via-usb-rs485-serial-interface-stuck-at-16ms-even-though-it-shou
#include <Windows.h>
#include <stdio.h>

HANDLE createSerialPort(char* name) {
	HANDLE hSerial = CreateFile(name, GENERIC_READ | GENERIC_WRITE, 0, 0,
			OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, 0);
	if (hSerial == INVALID_HANDLE_VALUE) {
		if (GetLastError() == ERROR_FILE_NOT_FOUND) {
			fprintf(stderr, "NATIVE ERROR: File not found: %s\n", name);
			fflush(stderr);
			return NULL;
		} else {
			fprintf(stderr, "NATIVE ERROR: when opening %s\n", name);
			fflush(stderr);
		}
		return NULL;
	}
	return hSerial;
}
boolean openSerialPort(HANDLE handle, int baud, int dataBits, int stopBits,
		int parity) {
	DCB dcbSerialParams = { 0 };
	dcbSerialParams.DCBlength = sizeof(dcbSerialParams);
	if (!GetCommState(handle, &dcbSerialParams)) {
		return 1;
	}
	dcbSerialParams.BaudRate = baud;
	dcbSerialParams.ByteSize = dataBits;
	dcbSerialParams.StopBits = stopBits;
	dcbSerialParams.Parity = NOPARITY;
	if (!SetCommState(handle, &dcbSerialParams)) {
		return 1;
	}

	COMMTIMEOUTS timeouts = { 0 };
	timeouts.ReadIntervalTimeout = MAXDWORD;
	timeouts.ReadTotalTimeoutConstant = 5;
	timeouts.ReadTotalTimeoutMultiplier = 5;
	timeouts.WriteTotalTimeoutConstant = 5;
	timeouts.WriteTotalTimeoutMultiplier = 5;
	if (!SetCommTimeouts(handle, &timeouts)) {
		return 1;
	}
	return 0;
}

int readFileByte(HANDLE handle) {
	DWORD dwRead;
	unsigned char chRead;

	if(ReadFile(handle, &chRead, 1, &dwRead, NULL)){
		if(dwRead>0){
			return chRead;
		}
		return -1;
	}
	else
		return -1;
}

int writeFileByte(HANDLE handle, int b) {
	DWORD dwBytesWrote = 0;
	if (!WriteFile(handle, &b, 1, &dwBytesWrote, NULL)) {
		fprintf(stderr, "NATIVE ERROR: Could not write to file\n");
		fflush(stderr);
		return -1;
	}
	return dwBytesWrote;
}

boolean closeSerialPort(HANDLE handle) {
	CloseHandle(handle);
	return 0;
}
