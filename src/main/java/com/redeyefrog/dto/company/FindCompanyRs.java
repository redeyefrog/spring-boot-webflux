package com.redeyefrog.dto.company;

import com.redeyefrog.dto.common.CommonRs;
import com.redeyefrog.model.Company;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class FindCompanyRs extends CommonRs {

    private List<Company> companies;

}
