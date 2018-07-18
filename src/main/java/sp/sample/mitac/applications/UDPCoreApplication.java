package sp.sample.mitac.applications;

import sp.sample.mitac.applications.interfaces.IUDPCoreApplication;
import sp.sample.mitac.domain.factories.interfaces.IEquipmentRecordFactory;
import sp.sample.mitac.domain.repositories.IEquipmentRecordRepository;
import sp.sample.mitac.domain.services.interfaces.IValidateService;
import sp.sample.mitac.shared.CustomCode;
import sp.sample.mitac.shared.CustomException;

import javax.inject.Inject;

public class UDPCoreApplication implements IUDPCoreApplication {

    private IValidateService validateService;
    private IEquipmentRecordFactory equipmentRecordFactory;
    private IEquipmentRecordRepository equipmentRecordRepository;

    @Inject
    public UDPCoreApplication(
            IValidateService validateService,
            IEquipmentRecordFactory equipmentRecordFactory,
            IEquipmentRecordRepository equipmentRecordRepository
            ) {
        this.validateService = validateService;
        this.equipmentRecordFactory = equipmentRecordFactory;
        this.equipmentRecordRepository = equipmentRecordRepository;
    }

    @Override
    public void execute(byte[] msg) {
        try {
            if (!this.validateService.checkCRC(msg)) {
                throw new CustomException(CustomCode.CRC_INVALID);
            }

            int[] msgAry = new int[msg.length];
            int i = 0;
            for (byte b : msg)
                msgAry[i++] = b & 0xff;

            this.equipmentRecordRepository.save(this.equipmentRecordFactory.create(msgAry[9], msgAry));

        } catch (CustomException ex) {
            ex.printStackTrace();
        }
    }
}
