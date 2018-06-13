package pr.types

import pr.CodeType

class AssetType extends CodeType {
    static constraints = {
        code unique: true
    }

    static mapping = {
        discriminator 'asset.type'
    }

    enum Value {
        CHAIRS('Chairs', 'chairs'),
        TABLES('Tables', 'tables'),
        INFLATABLES('Inflatables', 'inflatables'),
        COOLERS('Coolers', 'coolers'),
        FOOD_PROCESSEORS('Food Processors', 'food.processors')

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
