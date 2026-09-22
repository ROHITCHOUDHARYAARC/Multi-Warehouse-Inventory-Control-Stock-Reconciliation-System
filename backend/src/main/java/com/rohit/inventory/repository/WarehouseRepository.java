package com.rohit.inventory.repository;
import com.rohit.inventory.entity.*; import java.util.*; import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.*;
public interface WarehouseRepository extends JpaRepository<Warehouse,UUID>{
 Optional<Warehouse> findByCode(String code);
 Page<Warehouse> findByStatus(WarehouseStatus status,Pageable pageable);
 default Page<Warehouse> search(String search,WarehouseStatus status,Pageable pageable){
   if((search==null||search.isBlank())&&status==null)return findAll(pageable);
   if(search==null||search.isBlank())return findByStatus(status,pageable);
   String needle=search.trim().toLowerCase(Locale.ROOT);
   List<Warehouse> matches=findAll().stream().filter(w->status==null||w.getStatus()==status).filter(w->w.getCode().toLowerCase(Locale.ROOT).contains(needle)||w.getName().toLowerCase(Locale.ROOT).contains(needle)||(w.getCity()!=null&&w.getCity().toLowerCase(Locale.ROOT).contains(needle))).toList();
   int start=(int)Math.min(pageable.getOffset(),matches.size()),end=Math.min(start+pageable.getPageSize(),matches.size());
   return new PageImpl<>(matches.subList(start,end),pageable,matches.size());
 }
}
