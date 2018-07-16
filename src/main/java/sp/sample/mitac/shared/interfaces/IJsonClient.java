package sp.sample.mitac.shared.interfaces;

import sp.sample.mitac.shared.CustomException;

public interface IJsonClient {
    <T> T fromJson(String jsonString, Class<T> typeRef) throws CustomException;
    String toString(Object object) throws CustomException;
}
