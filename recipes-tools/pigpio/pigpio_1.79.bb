SUMMARY = "pigpio is a C library for the Raspberry which allows control of the General Purpose Input Outputs (GPIO)"
DESCRIPTION = "pigpio is a C library for the Raspberry which allows control of the General Purpose Input Outputs (GPIO)"
AUTHOR = "<komar@evologics.de>"

# FIXME: find out right license
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://UNLICENCE;md5=61287f92700ec1bdf13bc86d8228cd13"

SECTION = "utils"

SRC_URI = "git://github.com/joan2937/pigpio.git;protocol=https"

SRCREV = "c33738a320a3e28824af7807edafda440952c05d"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += "CC='${CC}'"
EXTRA_OEMAKE += "CROSS_PREFIX=${TARGET_PREFIX}"
# we don't need to strip, it will be stripped by build system
EXTRA_OEMAKE += "STRIP=echo"
EXTRA_OEMAKE += "PYINSTALLARGS='--root=$(DESTDIR) --prefix=${prefix}'"
TARGET_CC_ARCH += "${LDFLAGS}"

# gpio package will be empty with depends from all packages
ALLOW_EMPTY:${PN} = "1"
ALLOW_EMPTY:${PN}-dbg = "0"
ALLOW_EMPTY:${PN}-dev = "0"

FILES:${PN}-bin-pigs    = "${bindir}/pigs"
FILES:${PN}-bin-pig2vcd = "${bindir}/pig2vcd"

FILES:${PN}-bin-pigpiod    = "${bindir}/pigpiod"
RDEPENDS:${PN}-bin-pigpiod = "lib${PN}"

# *-bin package will be empty with depends from all *-bin-* packages
RDEPENDS:${PN}-bin = " ${PN}-bin-pigpiod ${PN}-bin-pigs ${PN}-bin-pig2vcd"
ALLOW_EMPTY:${PN}-bin = "1"

FILES_lib${PN}  = "${libdir}/lib${PN}.so.*"
FILES_lib${PN}  =+ "/opt/${PN}/cgi"

FILES_lib${PN}_if  = "${libdir}/lib${PN}_if.so.*"
FILES_lib${PN}_if2 = "${libdir}/lib${PN}_if2.so.*"

#FILES:${PN}-dev = "${includedir}/*.h"
FILES:${PN}-dev += "${libdir}/lib${PN}*.so"
FILES:${PN}-doc = "${mandir}"

FILES:${PN}-python2 = "${libdir}/python2*/*"
FILES:${PN}-python3 = "${libdir}/python3*/site-packages/"

FILES:${PN}-opt = "/opt/pigpio /opt/* /opt/pigpio/cgi"


PACKAGES =+ " ${PN}-bin-pigpiod ${PN}-bin-pigs ${PN}-bin-pig2vcd \
              lib${PN} lib${PN}_if lib${PN}_if2 \
              ${PN}-python2 ${PN}-python3  ${PN}-opt"

do_install() {
    oe_runmake install DESTDIR=${D} prefix=${prefix} mandir=${mandir}
}

inherit lib_package