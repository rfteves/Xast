package pr

import java.time.Instant

abstract class Stamp {

    Date createdOn
    Long createdById
    Date updatedOn
    Long updatedById

    User user
    static transients = ['user', 'userId']

    Stamp updateStamp(Stamp stamp) {
        createdOn = stamp.createdOn
        createdById = stamp.createdById
        updatedOn = stamp.updatedOn
        updatedById = stamp.updatedById
        this
    }

    Stamp initStamp(Long userId) {
        Date now = Date.from(Instant.now())

        createdOn = now
        createdById = userId
        updatedOn = now
        updatedById = userId

        this
    }

    void beforeValidate() {

        if (!user) {
            throw new NullPropertyException(transients)
        }

        doTimestamp()
    }
}
