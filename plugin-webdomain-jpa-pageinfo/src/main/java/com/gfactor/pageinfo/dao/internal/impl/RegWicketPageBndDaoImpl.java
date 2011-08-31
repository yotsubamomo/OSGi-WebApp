/**
 * 
 */
package com.gfactor.pageinfo.dao.internal.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.gfactor.pageinfo.dao.RegWicketPageBndDao;
import com.gfactor.pageinfo.jpa.Bndpageinfo;


/**
 * @author momo
 *
 */
public class RegWicketPageBndDaoImpl extends JpaDaoSupport implements RegWicketPageBndDao {
	
//	@PersistenceUnit
//	private EntityManagerFactory entityManagerFactory;
	
	@PersistenceContext
    private EntityManager em;
	
//	/**
//     * Constructor
//     */
//    @Autowired
//    public RegWicketPageBndDaoImpl(EntityManagerFactory entityManagerFactory) {
//    	System.out.println("to set entityManagerFactory ="+ entityManagerFactory);
//        super.setEntityManagerFactory(entityManagerFactory);
//    }
//    
    /**
     * get Bndpageinfo object by bndname,bndver and entry_point
     */
    
	public List<Bndpageinfo> findBndPageInfoList(String bndName, String bndVer,
			String entry_point) {
		
		
		return getJpaTemplate()
				.find("select r from Bndpageinfo r where r.bundle_name = ?1 and r.bundle_version =?2 and r.entry_point = ?3",
						bndName, bndVer, entry_point);
	}
	

	public void saveBndPageInfo(Bndpageinfo bndpageinfo) {
		System.out.println("save user " + bndpageinfo); 
		getJpaTemplate().persist(bndpageinfo);
	}


	public Bndpageinfo update(Bndpageinfo bndpageinfo) {
		return getJpaTemplate().merge(bndpageinfo);
		
	}


	public void delete(Bndpageinfo bndpageinfo) {
		System.out.println("delete on dao , obj = " + bndpageinfo);
		Bndpageinfo bndObj = getJpaTemplate().merge(bndpageinfo);
		System.out.println("delete on dao , merg obj = " + bndObj);
		getJpaTemplate().remove(bndObj);

	}

	@Override
	public Bndpageinfo getBndPageInfo(String bndName, String bndVer,
			String entry_point) {
		System.out.println("getBndPageInfo query value = " +bndName+","+bndVer+","+entry_point);
		Bndpageinfo bndRst = null;
		try{
			Query query = getJpaTemplate().getEntityManagerFactory().createEntityManager().createNamedQuery("QueryByName_Ver_Entrypoint");
			query.setParameter("bndName", bndName);
			query.setParameter("bndVer", bndVer); 
			query.setParameter("bndEntryPoint", entry_point);
			bndRst = (Bndpageinfo) query.getSingleResult();
		}catch (Exception e) {
			System.out.println("dao getBndPageInfo exception = " +e);
		}
		
		System.out.println("bndRst ============> "+ bndRst);
		return bndRst;
	}

	@Override
	public Bndpageinfo findByEntry(Bndpageinfo bndpageinfo) {
		
		return getJpaTemplate().find(Bndpageinfo.class, bndpageinfo.getId());
	}




}
