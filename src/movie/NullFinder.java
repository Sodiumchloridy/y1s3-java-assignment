package movie;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NullFinder {
    NullFinder() {};

    /**
     * set the original field in the movie object to the value of the not-null field
     * returns the updated movie object
     * basically a general and overall implementer of the update function
     * @param result, @param movie
     */
    public static Movie findNull(Result result, Movie movie) {
        Map<String, Object> nonNullAttribute = findNonNull(result);
        String nonNullKey = nonNullAttribute.keySet().iterator().next();
        return (setAttribute(movie, nonNullKey, nonNullAttribute.get(nonNullKey)));
    }

    /**
     * Find the field which is not null
     * returns the field name of not null, and the value itself
     * @param updatedResult
     */
    public static Map<String, Object> findNonNull(Result updatedResult) {
        Field[] fields = updatedResult.getClass().getDeclaredFields();
        Map<String, Object> attributeInfo = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true); // if field is private make it accessible
            try {
                if (field.get(updatedResult) != null
                        && !(field.get(updatedResult) instanceof List<?>
                                && ((List<?>) field.get(updatedResult)).isEmpty())) {
                    attributeInfo.put(field.getName(), field.get(updatedResult));
                    return attributeInfo; // title = f key: title value: f
                }
            } catch (IllegalAccessException e) {
                // if the access to field kena deny
                e.printStackTrace();
            }
        }
        return null; // no non-null value found, all values are null
    }
    /**
     * set the attribute in the movie object to the updated value
     * the error validation isnt done in a better way due to time constraint and the belief that the error is less likely to occur
     * @param movie, @param attributeName, @param attributeValue
     */
    public static Movie setAttribute (Movie movie, String attributeName, Object attributeValue){
        Class<?> classMovie = Movie.class;
        try {
            // get the field of the object with the attribute name
            Field movieField = classMovie.getDeclaredField(attributeName);

            // set accessible to true to access private
            movieField.setAccessible(true);

            // set the value of the field to the attribute value
            movieField.set(movie, attributeValue);

        } catch (NoSuchFieldException e) {
            System.out.println("error: no such field");
        } catch (IllegalAccessException e) {
            System.out.println("cant access priv");
        }
        return movie;
    }
}
