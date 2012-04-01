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
//http://www.lainoox.com/get-bytes-available-on-c-socket/
#include <Windows.h>
#include <stdio.h>
#include "win32termios.h"
#include <fcntl.h>
#include <errno.h>


void report(char * msg) {
	printf("REPORT: %s", msg);
}
void report_warning(char * msg) {
	printf("REPORT WARNING: %s", msg);
}
void report_error(char * msg) {
	printf("REPORT ERROR: %s", msg);
}


int openSerialPort(char* name, int baud) {
	int fd = OPEN(name, O_RDWR);
	struct termios tinfo;

	int baudBits = B0;
	switch (baud) {
	case 50:
		baudBits = B50;
		break;
	case 75:
		baudBits = B75;
		break;
	case 110:
		baudBits = B110;
		break;
	case 134:
		baudBits = B134;
		break;
	case 150:
		baudBits = B150;
		break;
	case 200:
		baudBits = B200;
		break;
	case 300:
		baudBits = B300;
		break;
	case 600:
		baudBits = B600;
		break;
	case 1200:
		baudBits = B1200;
		break;
	case 1800:
		baudBits = B1800;
		break;
	case 2400:
		baudBits = B2400;
		break;
	case 4800:
		baudBits = B4800;
		break;
	case 9600:
		baudBits = B9600;
		break;
	case 19200:
		baudBits = B19200;
		break;
	case 38400:
		baudBits = B38400;
		break;
	case 57600:
		baudBits = B57600;
		break;
	case 115200:
		baudBits = B115200;
		break;
	case 230400:
		baudBits = B230400;
		break;
	case 460800:
		baudBits = B460800;
		break;
	case 500000:
		baudBits = B500000;
		break;
	case 576000:
		baudBits = B576000;
		break;
	case 921600:
		baudBits = B921600;
		break;
	case 1000000:
		baudBits = B1000000;
		break;
	case 1152000:
		baudBits = B1152000;
		break;
	case 1500000:
		baudBits = B1500000;
		break;
	case 2000000:
		baudBits = B2000000;
		break;
	case 2500000:
		baudBits = B2500000;
		break;
	case 3000000:
		baudBits = B3000000;
		break;
	case 3500000:
		baudBits = B3500000;
		break;
	case 4000000:
		baudBits = B4000000;
		break;
	default:
		return -5; //unsupported baud rate
	}

	if (fd < 0)
		return -1;
	if (tcgetattr(fd, &tinfo) < 0) //unable to get serial parms
		return -2;
	if (cfsetspeed(&tinfo, baudBits) < 0) //error in cfsetspeed
		return -3;
	//TODO configure data bits, stop bits, and parity
	if (tcsetattr(fd, TCSANOW, &tinfo) < 0) //unable to set baud rate
		return -4;

	return fd;
}

int readBytes(int fd, char* buffer, int len) {
	int r, count = 0;
	int retry = 0;

	if (len == 0)
		return 0;
	if (len < 0)
		return -1;
	// non-blocking read mode
	fcntl(fd, F_SETFL, fcntl(fd, F_GETFL) | O_NONBLOCK);
	while (count < len) {
		r = READ(fd, buffer, len - count);

		if (r < 0 && errno != EAGAIN && errno != EINTR)
			return -2;
		else if (r > 0)
			count += r;
		else {
			// no data available right now, must wait
			fd_set fds;
			struct timeval t;
			FD_ZERO(&fds);
			FD_SET(fd, &fds);
			t.tv_sec = 0;
			t.tv_usec = 1;
			r = select(fd + 1, &fds, NULL, NULL, &t);

			if (r < 0)
				return -3;
			if (r == 0)
				return count; // timeout
		}
		retry++;
		if (retry > 10)
			return -100; // no input
	}
	fcntl(fd, F_SETFL, fcntl(fd, F_GETFL) & ~O_NONBLOCK);
	return count;
}

int available(int fd) {
	size_t nbytes = 0;
	if (ioctl(fd, FIONREAD, (char*) &nbytes) < 0) {
		return -1;
	}
	return nbytes;
}

int writeBytes(int fd, char* buffer, int len) {
	return WRITE(fd, buffer, len);
}

boolean closeSerialPort(int fd) {
	return CLOSE(fd) == 0;
}
