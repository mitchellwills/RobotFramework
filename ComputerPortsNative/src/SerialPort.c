/*
 * SerialPort.c
 *
 *  Created on: Mar 27, 2012
 *      Author: Mitchell
 */

//Based on http://www.robbayer.com/files/serial-win.pdf
//Also see http://msdn.microsoft.com/en-us/library/ms810467.aspx
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
	dcbSerialParams.BaudRate = CBR_19200;
	dcbSerialParams.ByteSize = 8;
	dcbSerialParams.StopBits = ONESTOPBIT;
	dcbSerialParams.Parity = NOPARITY;
	if (!SetCommState(handle, &dcbSerialParams)) {
		return 1;
	}

	COMMTIMEOUTS timeouts = { 0 };
	timeouts.ReadIntervalTimeout = 1;
	timeouts.ReadTotalTimeoutConstant = 1;
	timeouts.ReadTotalTimeoutMultiplier = 1;
	timeouts.WriteTotalTimeoutConstant = 1;
	timeouts.WriteTotalTimeoutMultiplier = 1;
	if (!SetCommTimeouts(handle, &timeouts)) {
		return 1;
	}
	return 0;
}

int readSerialPort(HANDLE handle, char* buffer, int bufferSize) {
	printf("Reading\n");
	fflush(stdout);
	DWORD dwBytesRead = 0;
	if (!ReadFile(handle, buffer, bufferSize, &dwBytesRead, NULL)) {
		printf("Reading Error\n");
		fflush(stdout);
		return -1;
	}
	return dwBytesRead;
}

int readFileByte(HANDLE handle) {
	DWORD dwRead;
	char chRead;

	if(ReadFile(handle, &chRead, 1, &dwRead, NULL))
		return chRead;
	else
		return -1;
}

int readFile(HANDLE handle, char* buffer, int off, int len) {
	DWORD dwRead;
	if(ReadFile(handle, buffer+off, len, &dwRead, NULL))
		return dwRead;
	else
		return -1;
}

int writeFile(HANDLE handle, char* buffer, int writeSize) {
	printf("Writing %s\n", buffer);
	fflush(stdout);
	DWORD dwBytesWrote = 0;
	if (!WriteFile(handle, buffer, writeSize, &dwBytesWrote, NULL)) {
		return -1;
	}
	return dwBytesWrote;
}
boolean closeSerialPort(HANDLE handle) {
	CloseHandle(handle);
	return 0;
}
