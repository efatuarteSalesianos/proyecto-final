package com.salesianostriana.dam.finalapi.repositories;

import com.salesianostriana.dam.finalapi.dtos.site.GetListSiteDto;
import com.salesianostriana.dam.finalapi.dtos.site.GetSiteDto;
import com.salesianostriana.dam.finalapi.models.Site;
import com.salesianostriana.dam.finalapi.models.SiteTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Long> {

    List<Site> findByNameContaining(String name);

    List<Site> findByPostalCode(String postalCode);

    List<Site> findByCity(String city);

    @Query("""
            select new com.salesianostriana.dam.finalapi.dtos.site.GetListSiteDto(
                s.name, s.address, s.city, s.postalCode, s.totalComments, s.rate, s.scaledFileUrl, s.liked
            )
            from Site s
            where s.rate >= :rate
            """)
    List<GetListSiteDto> findByRateGreaterThan(@Param("rate") Double rate);

    @Query("""
            select new com.salesianostriana.dam.finalapi.dtos.site.GetListSiteDto(
                s.name, s.address, s.city, s.postalCode, s.totalComments, s.rate, s.scaledFileUrl, s.liked
            )
            from Site s
            where s.type = :type
            """)
    List<GetListSiteDto> findByType(@Param("type") SiteTypes type);

    @Query("""
            select new com.salesianostriana.dam.finalapi.dtos.site.GetListSiteDto(
                s.name, s.address, s.city, s.postalCode, s.totalComments, s.rate, s.scaledFileUrl, s.liked
            )
            from Site s
            where s.propietario.id = :id
            """)
    List<GetListSiteDto> findByPropietarioId(@Param("id") Long id);
}
