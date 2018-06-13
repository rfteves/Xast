package pr.types

import pr.CodeType

class OrganizationType extends CodeType {
    static constraints = {
        code unique: true
    }

    static mapping = {
        discriminator 'agency.type'
    }

    enum Value {
        INDIVIDUAL('Individual', 'ind'),
        PARTNERSHIP('Partnership', 'prt'),
        LLC('LLC', 'llc'),
        CORPORATION('Corporation', 'crp'),
        CONCESSION('Concession', 'csn'),
        OTHER('Other', 'etc')

        private final String name
        private final String code

        Value(String name, String code) {
            this.name = name
            this.code = code
        }

        String toString() {
            this.code
        }

        OrganizationType getType() {
            OrganizationType.findByCode(code)
        }
    }
}
