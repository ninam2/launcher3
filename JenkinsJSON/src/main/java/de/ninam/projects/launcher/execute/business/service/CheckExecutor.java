package de.ninam.projects.launcher.execute.business.service;


import de.ninam.projects.launcher.execute.business.domain.Check;
import de.ninam.projects.launcher.execute.business.domain.CheckResult;

import java.util.List;


public interface CheckExecutor {

    /**
     * @param check
     * @return Never an empty collection. In case of "everything is fine" a "green" result check must be returned!
     */
    List<CheckResult> executeCheck(Check check);

    boolean isApplicable(Check check);
}
