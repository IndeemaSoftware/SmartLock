import time
try:
    import RPi.GPIO as GPIO
except RuntimeError:
    print("Error importing RPi.GPIO!  This is probably because you need superuser privileges.  "
          "You can achieve this by using 'sudo' to run your script")


def open_door():
    pin_number = 7
    time_out = 1

    print('Open door...')
    rpi_info = GPIO.RPI_INFO

    if rpi_info['P1_REVISION'] == '2':
        pin_number = 7
    elif rpi_info['P1_REVISION'] == '3':
        pin_number = 21

    try:
        GPIO.cleanup()
    except RuntimeWarning:
        print('Nothing to clean up')

    GPIO.setmode(GPIO.BCM)
    GPIO.setup(pin_number, GPIO.OUT)
    GPIO.output(pin_number, GPIO.HIGH)
    time.sleep(time_out)
    GPIO.output(pin_number, GPIO.LOW)
    GPIO.cleanup()


if __name__ == '__main__':
    open_door()


