package za.co.nedbank.service.dao;

/**
 *
 * @author NB320512
 */
public record CaseInfo(
        String caseId,
        String processBPDID,
        String processState,
        String processType,
        String properties,
        String createDate,
        String completeDate) {}
