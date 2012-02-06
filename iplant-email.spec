%define __jar_repack %{nil}
%define debug_package %{nil}
%define __strip /bin/true
%define __os_install_post   /bin/true
%define __check_files /bin/true
Summary: iplant-email
Name: iplant-email
Version: 0.1.0
Release: 1
Epoch: 0
BuildArchitectures: noarch
Group: Applications
BuildRoot: %{_tmppath}/%{name}-%{version}-buildroot
License: BSD
Provides: iplant-email
Source0: %{name}-%{version}.tar.gz

%description
iPlant Email

%pre
getent group iplant > /dev/null || groupadd -r iplant
getent passwd iplant > /dev/null || useradd -r -g iplant -d /home/iplant -s /bin/bash -c "User for the iPlant services." iplant
exit 0

%prep
%setup -q
mkdir -p $RPM_BUILD_ROOT/etc/init.d/

%build
unset JAVA_OPTS
lein deps
lein uberjar

%install
install -d $RPM_BUILD_ROOT/usr/local/lib/iplant-email/
install -d $RPM_BUILD_ROOT/var/run/iplant-email/
install -d $RPM_BUILD_ROOT/var/lock/subsys/iplant-email/
install -d $RPM_BUILD_ROOT/var/log/iplant-email/
install -d $RPM_BUILD_ROOT/etc/iplant-email/

install iplant-email $RPM_BUILD_ROOT/etc/init.d/
install iplant-email-1.0.0-SNAPSHOT-standalone.jar $RPM_BUILD_ROOT/usr/local/lib/iplant-email/
install conf/log4j.properties $RPM_BUILD_ROOT/etc/iplant-email/
install conf/iplant-email.properties $RPM_BUILD_ROOT/etc/iplant-email/
install conf/*.st $RPM_BUILD_ROOT/etc/iplant-email/

%post
/sbin/chkconfig --add iplant-email

%preun
if [ $1 -eq 0 ] ; then
	/sbin/service iplant-email stop >/dev/null 2>&1
	/sbin/chkconfig --del iplant-email
fi

%postun
if [ "$1" -ge "1" ] ; then
	/sbin/service iplant-email condrestart >/dev/null 2>&1 || :
fi

%clean
lein clean
rm -r lib/*

%files
%attr(-,iplant,iplant) /usr/local/lib/iplant-email/
%attr(-,iplant,iplant) /var/run/iplant-email/
%attr(-,iplant,iplant) /var/lock/subsys/iplant-email/
%attr(-,iplant,iplant) /var/log/iplant-email/
%attr(-,iplant,iplant) /etc/iplant-email/

%config %attr(0644,iplant,iplant) /etc/iplant-email/log4j.properties
%config %attr(0644,iplant,iplant) /etc/iplant-email/iplant-email.properties

%attr(0755,root,root) /etc/init.d/iplant-email
%attr(0644,iplant,iplant) /usr/local/lib/iplant-email/iplant-email-1.0.0-SNAPSHOT-standalone.jar


