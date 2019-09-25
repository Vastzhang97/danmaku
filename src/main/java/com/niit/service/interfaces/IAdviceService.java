package com.niit.service.interfaces;

import com.niit.dao.impl.AdviceDaoImpl;
import com.niit.entity.AdviceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface IAdviceService {

    int addAdvice(AdviceEntity adviceEntity);
}
