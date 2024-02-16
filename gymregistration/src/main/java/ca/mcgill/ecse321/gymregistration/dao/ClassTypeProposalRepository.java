package ca.mcgill.ecse321.gymregistration.dao;

import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.model.ClassTypeProposal;
import org.springframework.data.repository.CrudRepository;

public interface ClassTypeProposalRepository extends CrudRepository<ClassTypeProposal, Integer> {
    ClassTypeProposal findClassTypeProposalByClassType(ClassType classType);
}
