package com.example.isupervision.services;

import com.example.isupervision.entities.users.Student;
import com.example.isupervision.entities.works.Work;
import com.example.isupervision.entities.works.WorkLimits;
import com.example.isupervision.entities.works.WorkType;
import com.example.isupervision.repository.StudentRepository;
import com.example.isupervision.repository.WorkLimitsRepo;
import com.example.isupervision.repository.WorkRepository;
import com.example.isupervision.web.dto.WorkDTO;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.isupervision.entities.works.WorkType.*;

@Service
public class WorkServiceImpl implements WorkService{

    private final WorkRepository workRepository;
    private final WorkLimitsRepo workLimitsRepository;
    private final StudentRepository studentRepository;

    public WorkServiceImpl(
            WorkRepository workRepository,
            WorkLimitsRepo workLimitsRepository,
            StudentRepository studentRepository
    ){
        this.workRepository = workRepository;
        this.workLimitsRepository = workLimitsRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void saveWork(WorkDTO workDTO) {
        Work work = new Work();
        work.setTitle(workDTO.getTitle());
        work.setDescription(workDTO.getDescription());
        work.setDeadline(workDTO.getDeadline());
        work.setTermDate(workDTO.getTermDate());
        work.setType(workDTO.getType());
        work.setApproved(workDTO.isApproved());

        workRepository.save(work);
    }

    @Override
    public void updateLimits(WorkLimits limits) {
        workLimitsRepository.save(limits);
    }

    @Override
    public WorkLimits fetchLimits() {
        Date date = new Date();
        Integer year = date.getYear();
        if (workLimitsRepository.getWorkLimitsByYear(year) == null){
            WorkLimits newWorkLimits = new WorkLimits(year);
            workLimitsRepository.save(newWorkLimits);
        }
        return workLimitsRepository.getWorkLimitsByYear(year);
    }

    @Override
    public ArrayList<Work> fetchUserWorksByEmail(String email) {
        ArrayList<Work> works = workRepository.findAllByStudentEmail(email);
        if (!works.isEmpty()) {
            works.sort(Comparator.comparing(Work::getType));
        }
        return works;
    }

    @Override
    public List<Work> fetchWorksByType(WorkType type) {
        ArrayList<Work> listOfWork = workRepository.findAllByType(type);
        Collections.sort(listOfWork);
        return listOfWork;
    }

    @Override
    public Work getWorkById(Long id) {
        return workRepository.getById(id);
    }

    @Override
    public void updateWork(WorkDTO editedWork, Long id) {
        Work existingWork = workRepository.getById(id);
        existingWork.setTitle(editedWork.getTitle());
        existingWork.setDescription(editedWork.getDescription());
        existingWork.setTermDate(editedWork.getTermDate());
        existingWork.setDeadline(editedWork.getDeadline());
        existingWork.setApproved(editedWork.isApproved());

        workRepository.save(existingWork);
    }

    @Override
    public void deleteWorkById(Long id) {
        workRepository.deleteById(id);
    }

    @Override
    public void approveWorkById(Long id) {
        Work work = getWorkById(id);
        work.setApproved(true);
        workRepository.save(work);
    }

    @Override
    public ArrayList<Work> fetchRelatedUserWorks(Student student) {
        ArrayList<Work> works = new ArrayList<>();
        if (!student.hasProject()){
            works = workRepository.findAllByTypeAndApprovedAndTakenFalse(PROJECT, true);
        }else if (!student.hasBachelor()){
            works = workRepository.findAllByTypeAndApprovedAndTakenFalse(BACHELOR, true);
        }else if (!student.hasMaster()){
            works = workRepository.findAllByTypeAndApprovedAndTakenFalse(MASTER, true);
        }else return null;

        Collections.sort(works);
        return works;
    }

    @Override
    public void takeWork(Long id, Student student) {
        Work work = workRepository.getById(id);
        WorkType workType = work.getType();

        if (!work.isTaken()){
            work.setTaken(true);
            student.addWork(work);

            if (workType == PROJECT){
                student.setHasProject(true);
            }else if (workType == BACHELOR){
                student.setHasBachelor(true);
            }else if (workType == MASTER){
                student.setHasMaster(true);
            }

            workRepository.setStudent(id, student);
            studentRepository.save(student);
        }
    }


}
