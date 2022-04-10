# app-inventor-ransom
an app inventor 2 based ransomware
## requirements
please follow the [doc](https://docs.google.com/document/d/1Xc9yt02x3BRoq5m1PJHBr81OOv69rEBy8LVG_84j9jc/pub) to set up local MIT dev server. here we provided some patches for orignal server for better developing experience.
### patches for Vagrantfile
since Windows does not support ```:``` character in path name, this will case crashes while uploading ```.aix``` extension file if [using virtualbox on Windows as hosting platform](https://community.appinventor.mit.edu/t/getting-server-error-could-not-add-form-please-try-again-later-when-trying-to-add-screen-on-locally-running-app-inventor/50034). as a result, we recommand you using docker as provider in linux based system.
