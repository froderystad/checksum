Checksum
========

This project investigates how to calculate the checksum of multiple files at once. 

The MD5 checksum is calculated using [DigestUtils](http://commons.apache.org/proper/commons-codec/archives/1.9/apidocs/org/apache/commons/codec/digest/DigestUtils.html) 
from Apache's [Commons Codec](http://commons.apache.org/proper/commons-codec/) library.

The tests depend on some text files with CRLF and others with LF line endings. Therefore, 
`.gitattributes` is configured so that EOL conversion is disabled. 
