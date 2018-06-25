package com.gotkcups.exception

class RecordNotFoundException extends RuntimeException {


    RecordNotFoundException(String domainName, String findByMethodName, Object value) {
        println "\nerror $domainName"
        println "error $findByMethodName"
        println "error $value"
    }


}
