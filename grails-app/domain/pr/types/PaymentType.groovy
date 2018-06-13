package pr.types

import pr.CodeType

class PaymentType extends CodeType {
    static constraints = {
        code unique: true
    }

    static mapping = {
        discriminator 'payment.type'
    }

    enum Value {
        SALE('Sale', 'sale'),
        CREDIT('Credit', 'credit'),
        REFUND('Refund', 'refund'),
        SETTLEMENT('Settlement', 'settlement'),
        PENALTY('Penalty', 'penalty')

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
