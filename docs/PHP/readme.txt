获取并解压 PHP 源代码:

tar zxvf php-x.x.x
配置并构建 PHP。在此步骤您可以使用很多选项自定义 PHP，例如启用某些扩展等。 运行 ./configure --help 命令来获得完整的可用选项清单。 在本示例中，我们仅进行包含 PHP-FPM 和 MySQL 支持的简单配置。

cd ../php-x.x.x
./configure --enable-fpm --with-mysql
make
sudo make install
创建配置文件，并将其复制到正确的位置。

cp php.ini-development /usr/local/php/php.ini
cp /usr/local/etc/php-fpm.conf.default /usr/local/etc/php-fpm.conf
cp sapi/fpm/php-fpm /usr/local/bin
需要着重提醒的是，如果文件不存在，则阻止 Nginx 将请求发送到后端的 PHP-FPM 模块， 以避免遭受恶意脚本注入的攻击。

将 php.ini 文件中的配置项 cgi.fix_pathinfo 设置为 0 。

打开 php.ini:

vim /usr/local/php/php.ini
定位到 cgi.fix_pathinfo= 并将其修改为如下所示：

cgi.fix_pathinfo=0
在启动服务之前，需要修改 php-fpm.conf 配置文件，确保 php-fpm 模块使用 www-data 用户和 www-data 用户组的身份运行。

vim /usr/local/etc/php-fpm.conf
找到以下内容并修改：

; Unix user/group of processes
; Note: The user is mandatory. If the group is not set, the default user's group
;       will be used.
user = www-data
group = www-data
然后启动 php-fpm 服务：

/usr/local/bin/php-fpm
本文档未涵盖对 php-fpm 进行进一步配置的信息，如果您需要更多信息，请查阅相关文档。



http://www.linuxfromscratch.org/blfs/view/svn/general/libxml2.html
Installation of libxml2

Install libxml2 by running the following commands:

./configure --prefix=/usr --disable-static --with-history &&
make
If you downloaded the testsuite, issue the following command:

tar xf ../xmlts20130923.tar.gz
To test the results, issue: make check > check.log. A summary of the results can be obtained with grep -E '^Total|expected' check.log. If Valgrind-3.11.0 is installed and you want to check memory leaks, replace check with check-valgrind.

Now, as the root user:

make install
Command Explanations

--disable-static: This switch prevents installation of static versions of the libraries.

--with-history: This switch enables Readline support when running xmlcatalog or xmllint in shell mode.

--with-python=/usr/bin/python3: Add this switch if you want libxml2 to use Python3 instead of Python2.

Contents

Installed Programs:
xml2-config, xmlcatalog and xmllint
Installed Libraries:
libxml2.so and optionally, the libxml2mod.so Python module
Installed Directories:
/usr/include/libxml2, /usr/lib/cmake/libxml2, /usr/share/doc/libxml2-2.9.3, /usr/share/doc/libxml2-python-2.9.3, and /usr/share/gtk-doc/html/libxml2
Short Descriptions

xml2-config
determines the compile and linker flags that should be used to compile and link programs that use libxml2.
xmlcatalog
is used to monitor and manipulate XML and SGML catalogs.
xmllint
parses XML files and outputs reports (based upon options) to detect errors in XML coding.
libxml2.so
provides functions for programs to parse files that use the XML format.
Last updated on 2015-11-23 12:07:30 -0600
