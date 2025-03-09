DESCRIPTION = "A library to control Raspberry Pi GPIO channels"
HOMEPAGE = "https://projects.drogon.net/raspberry-pi/wiringpi/"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://COPYING.LESSER;md5=e6a600fd5e1d9cbde2d983680233ad02"

DEPENDS += "virtual/crypt"

SRCREV = "4639b7ac45ff87a9c2271a3d44f7fccb618c88ff"

S = "${WORKDIR}/git"

SRC_URI = " \
            git://github.com/WiringPi/WiringPi;branch=master;protocol=https \
          "

COMPATIBLE_MACHINE = "^rpi$"

CFLAGS:prepend = "-I${S}/wiringPi -I${S}/devLib "

EXTRA_OEMAKE += "'INCLUDE_DIR=${D}${includedir}' 'LIB_DIR=${D}${libdir}'"
EXTRA_OEMAKE += "'DESTDIR=${D}/usr' 'PREFIX=""'"

do_compile() {
    oe_runmake -C devLib
    oe_runmake -C wiringPi
    oe_runmake -C gpio 'LDFLAGS=${LDFLAGS} -L${S}/wiringPi -L${S}/devLib'
}

do_install() {
    oe_runmake -C devLib install
    oe_runmake -C wiringPi install
    oe_runmake -C gpio install
}