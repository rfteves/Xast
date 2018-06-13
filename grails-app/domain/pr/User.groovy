package pr

import static Constraints.NOT_EMPTY_NULL

class User extends Stamp {

    // Foreign Key
    Long agencyId

    // Properties
    String username
    String password
    String firstName
    String lastName
    String emailAddress
    String oathToken
    Boolean isActive = true
    Boolean isHuman = true
    Boolean isLoginEnabled = true

    static constraints = {
        username NOT_EMPTY_NULL, maxSize: 16
        password NOT_EMPTY_NULL, maxSize: 100
    }

    static mapping = {
    }
}
