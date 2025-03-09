SUMMARY = "Utility to show time, humidity and temperature with 4-digit display"

LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4ae09d45eac4aa08d013b5f2e01c67f6"

SRC_URI = "git://github.com/TheRedRover/rpi-utils/;branch=master;protocol=https \
    file://temp-hum-clock.service \
"

SRCREV = "4933d8e652db57690243ccdc2c4e2ca49f489828"
PV = "1.5+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS = " pigpio"
RDEPENDS:${PN} = " pigpio bash"

inherit cmake
inherit systemd

SYSTEMD_SERVICE:${PN} = "temp-hum-clock.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/temp-hum-clock.service ${D}${systemd_system_unitdir}/temp-hum-clock.service
}

FILES:${PN} += "${systemd_system_unitdir}/temp-hum-clock.service"
