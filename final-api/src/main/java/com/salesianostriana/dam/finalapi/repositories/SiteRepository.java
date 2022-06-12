package com.salesianostriana.dam.finalapi.repositories;

import com.salesianostriana.dam.finalapi.dtos.site.GetListSiteDto;
import com.salesianostriana.dam.finalapi.models.Site;
import com.salesianostriana.dam.finalapi.models.SiteTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SiteRepository extends JpaRepository<Site, Long> {

    List<Site> findByNameContaining(String name);

    List<Site> findByPostalCode(String postalCode);

    List<Site> findByCity(String city);

    @Query(value = """
            select new com.salesianostriana.dam.finalapi.dtos.site.GetListSiteDto(
                s.id, s.name, s.address, s.city, s.phone, s.postalCode, (select count(*) from Comment c where c.site.id = s.id), (select avg(c.rate) from Comment c where c.site.id = s.id), s.scaledFile, s.liked
            )
            from Site s
            where (select avg(c.rate) from Comment c where c.site.id = s.id) >= :rate
            """)
    List<GetListSiteDto> findByRateGreaterThan(@Param("rate") Double rate);

    @Query(value = """
            select new com.salesianostriana.dam.finalapi.dtos.site.GetListSiteDto(
                s.id, s.name, s.address, s.city, s.phone, s.postalCode, (select count(*) from Comment c where c.site.id = s.id), (select avg(c.rate) from Comment c where c.site.id = s.id), s.scaledFile, s.liked
            )
            from Site s
            where s.type = :type
            """)
    List<GetListSiteDto> findByType(@Param("type") SiteTypes type);

    @Query(value = """
            select new com.salesianostriana.dam.finalapi.dtos.site.GetListSiteDto(
                s.id, s.name, s.address, s.city, s.phone, s.postalCode, (select count(*) from Comment c where c.site.id = s.id), (select avg(c.rate) from Comment c where c.site.id = s.id), s.scaledFile, s.liked
            )
            from Site s
            where s.propietario.username = :username
            """)
    List<GetListSiteDto> findByPropietarioUsername(@Param("username") String username);
}
