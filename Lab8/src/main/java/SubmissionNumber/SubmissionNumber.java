package SubmissionNumber;

import javax.enterprise.inject.Produces;
import java.io.Serializable;

public class SubmissionNumber implements Serializable {
    private java.util.Random random =
            new java.util.Random(System.currentTimeMillis());

    java.util.Random getRandom() {
        return random;
    }

    @Produces
    @Random
    int next() {
        return getRandom().nextInt(1000);
    }
}
