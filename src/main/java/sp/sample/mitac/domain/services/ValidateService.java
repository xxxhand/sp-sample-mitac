package sp.sample.mitac.domain.services;

import sp.sample.mitac.domain.services.interfaces.IValidateService;
import sp.sample.mitac.shared.CustomException;

public class ValidateService implements IValidateService {

    @Override
    public boolean checkCRC(int[] msg) throws CustomException {

        return true;
    }
}
