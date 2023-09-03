package com.cg.hrms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrms.entities.Employee;
import com.cg.hrms.entities.Title;
import com.cg.hrms.exceptions.CustomException;
import com.cg.hrms.repos.IEmployeeRepo;
import com.cg.hrms.repos.ITitleRepo;

import jakarta.transaction.Transactional;

import java.sql.Date;

@Service
public class TitleServiceImpl implements ITitleService {

	@Autowired
	ITitleRepo titrepo;
	
	@Autowired
	IEmployeeRepo empRepo;

	// 1 Add new TITLE
	@Override
    public String addTitle(Title title) {
        Employee emp = empRepo.findByEmpNo(title.getId().getEmpNo().getEmpNo());
        if (emp == null)
            throw new CustomException("Incorrect employee id");
        if (title.getId().getFromDate().compareTo(title.getToDate()) > 0)
            throw new CustomException("From date should appear before To date");
        titrepo.save(title);
        return "New Employee details  added Successfully";
	}

	// 2. Fetch all titles objects
	@Override
	public List<Title> fetchAllTitles() {
		return titrepo.findAll();
	}

	// 3. get title By empNo FromDate and Title
	@Override
    public Title getTitleByEmpNoFromDateTitle(int empNo, Date fromDate, String title) {
        Optional<Employee> emp = empRepo.findById(empNo);
        if (emp.isPresent()) {
            Employee employee = new Employee();
            employee.setEmpNo(empNo);
            return titrepo.findById_EmpNoAndId_FromDateAndId_Title(employee, fromDate, title);
        } else {
            throw new CustomException("Employee with this EmpNo, FromDate AndTitle is not exists");
        }
    }

	// 4. Fetch all titles objects by from date
	@Override
	public List<Title> getTitlesByFromDate(Date fromDate) {
		List<Title> list = titrepo.findById_FromDate(fromDate);
		if (list.size() != 0) {
			return list;
		} else {
			throw new CustomException("Employee With this FromDate is not Exists...");
		}
	}

	// 5. Fetch all titles objects by title
	@Override
	public List<Title> getTitlesByTitle(String title) {
		List<Title> list = titrepo.findById_Title(title);
		if (list.size() != 0) {
			return list;
		} else {
			throw new CustomException("Employee With this Title is not Exists...");
		}
	}

	// 6. Fetch all titles objects by title and fromdate-
	@Override
	public List<Title> getTitleByTitleAndFromDate(String title, Date fromDate) {
		List<Title> list = titrepo.findById_TitleAndId_FromDate(title, fromDate);
		if (list.size() != 0) {
			return list;
		} else {
			throw new CustomException("Employee With this Title And FromDate is not Exists...");
		}
	}

	// 7.Fetch all titles objects by empno and fromdate
	@Override
	public List<Title> getTitleByEmpNoAndFromDate(Employee empNo, Date fromDate) {
		List<Title> list = titrepo.findById_EmpNoAndId_FromDate(empNo, fromDate);
		if (list.size() != 0) {
			return list;
		} else {
			throw new CustomException("Employee With this EmpNo And FromDate is not Exists...");
		}
	}

	// 8.Fetch all titles objects by empno and title
	@Override
	public List<Title> getTitleByEmpNoAndTitle(Employee empNo, String title) {
		List<Title> list = titrepo.findById_EmpNoAndId_Title(empNo, title);
		if (list.size() != 0) {
			return list;
		} else {
			throw new CustomException("Employee With this EmpNo And Title is not exists...");
		}
	}

//    //9.Update titles details for the given fromdate
	@Override
	public List<Title> updateTitleByFromDate(Date fromDate, Title title) {
		List<Title> t1 = titrepo.findById_FromDate(fromDate);
		if (t1.size() != 0) {
			if (title.getToDate().compareTo(title.getId().getFromDate()) < 0) {
				throw new CustomException("From date should appear before To date");
			}
			for (Title t : t1) {
				if (title.getToDate() != null) {
					t.setToDate(title.getToDate());
				}
			}
			return titrepo.saveAll(t1);
		} else {
			throw new CustomException("Employee details with given Date is not exist...");
		}
	}

	// 10. Update titles details for the given title
	@Override
	public List<Title> updateTitleByTitle(String title, Title title1) {
		List<Title> t1 = titrepo.findById_Title(title);
		if (t1.size() != 0) {
			if (title1.getToDate().compareTo(title1.getId().getFromDate()) < 0) {
				throw new CustomException("From date should appear before To date");
			}
			for (Title t : t1) {
				if (title1.getToDate() != null) {
					t.setToDate(title1.getToDate());
				}
			}

			return titrepo.saveAll(t1);
		} else {
			throw new CustomException("Employee details with given Title is not exist...");
		}
	}

	// 11.Update titles details for the given employee no
	@Override
    public List<Title> updateTitleByEmpNo(int empNo, Title title) {
        if (!empRepo.findById(empNo).isPresent())
            throw new CustomException("Invalid employee Id");
        Employee emp = new Employee();
        emp.setEmpNo(empNo);
        List<Title> t1 = titrepo.findById_EmpNo(emp);
        if (t1.size() != 0) {
            if (title.getToDate().compareTo(title.getId().getFromDate()) < 0) {
                throw new CustomException("From date should appear before To date");
            }
            for (Title t : t1) {
                if (title.getToDate() != null)
                    t.setToDate(title.getToDate());
            }
            return titrepo.saveAll(t1);
        } else {
            throw new CustomException("Employee details with given Employee Number is not exist...");
        }
    }
	
	
	// 12.Update title by empno, fromdate, title

	@Override
	public String updateTitleByEmpNoAndFromDate(Employee empNo, Date fromDate, String title, Title title2) {
		Title t = titrepo.findById_EmpNoAndId_FromDateAndId_Title(empNo, fromDate, title);
		if (t != null) {
			if (title2.getToDate().compareTo(title2.getId().getFromDate()) < 0) {
				throw new CustomException("From date should appear before To date");
			}
			if (title2.getToDate() != null)
				t.setToDate(title2.getToDate());
			titrepo.saveAndFlush(t);
			return "Title updated Successfully";
		} else {
			throw new CustomException("Employee with this EmpNo, From Date And title is not exist...");
		}
	}

	// 13.Delete title by empno, from date and title name.
	@Override
	@Transactional // add exception
	public String deleteTitleByEmpNoFromDateAndTitle(Employee empNo, Date fromDate, String title) {
		Title tit = titrepo.findById_EmpNoAndId_FromDateAndId_Title(empNo, fromDate, title);
		if (tit != null) {
			titrepo.deleteById_EmpNoAndId_FromDateAndId_Title(empNo, fromDate, title);
			return "Title deleted Successfully";
		} else {
			throw new CustomException("Employee details with given EmpNo, FromDate And title is not exist...");
		}
	}
	// 14.Delete titles by empno

	@Override
	@Transactional
	public List<Title> deleteTitleByEmpNo(Employee empNo) {
		List<Title> tit = titrepo.findById_empNo(empNo);
		if (tit.size() != 0) {
			return titrepo.deleteById_EmpNo(empNo);

		} else {
			throw new CustomException("Employee details with given Employee Number is not exist...");
		}
	}

	// 15.Delete titles by fromdate
	@Override
	@Transactional
	public List<Title> deleteTitleByFromDate(Date fromDate) {
		List<Title> tit = titrepo.findById_FromDate(fromDate);
		if (tit.size() != 0) {
			return titrepo.deleteById_FromDate(fromDate);

		} else {
			throw new CustomException("Employee details with given Date is not exist...");
		}
	}

	// 16.Delete titles by title
	@Override
	public List<Title> deleteTitleByTitle(String title) {
		List<Title> tit = titrepo.findById_Title(title);
		if (tit.size() != 0) {
			return titrepo.deleteById_Title(title);

		} else {
			throw new CustomException("Employee details with given Title is not exist...");
		}
	}
}
