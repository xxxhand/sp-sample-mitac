package sp.sample.mitac.domain.services.interfaces;

import sp.sample.mitac.shared.CustomException;

public interface IValidateService {
    boolean checkCRC(int[] msg) throws CustomException;
}
