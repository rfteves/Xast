package pr

import pr.types.OrganizationType

class Organization extends Stamp {

    // Foreign Keys
    OrganizationType agencyType

    // Properties
    String agencyCode
    String agencyName
    String agencyTypeCode

    static hasMany = [
            accounts           : Account,
    ]

    static constraints = {
        agencyAccountId nullable: true
        agencyCode unique: true, maxSize: 50
        agencyName blank: false, maxSize: 50
        agencyTypeCode CA_CODE
    }

    @SuppressWarnings(['GrailsDuplicateMapping'])
    static mapping = {
        id column: 'agency_id'
        id generator: 'identity'
    }

    Account getAgencyAccount() {
        Account.findById(agencyAccountId)
    }

    @Override
    void beforeValidate() {
        super.beforeValidate()

        agencyTypeCode = agencyType?.displayCode
    }
}
