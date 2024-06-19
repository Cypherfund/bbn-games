package com.cypherfund.bbn.services.contract;

import com.cypherfund.bbn.dao.entity.Housemate;

import java.util.List;

public interface IHousemateService {
    public void addHousemate(Housemate housemate);
    public List<Housemate> getHousemates();
}
