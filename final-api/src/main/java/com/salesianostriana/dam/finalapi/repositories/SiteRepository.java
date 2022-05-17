package com.salesianostriana.dam.finalapi.repositories;

import com.salesianostriana.dam.finalapi.dtos.site.GetSiteDto;
import com.salesianostriana.dam.finalapi.models.Site;
import com.salesianostriana.dam.finalapi.models.SiteTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Long> {

    // Buscar todos los sitios que tengan un nombre que contenga el texto que se le pasa
    List<Site> findByNameContaining(String name);

    // Buscar todos los sitios que tengan un determinado codigo postal
    List<Site> findByPostalCode(String postalCode);

    // Buscar todos los sitios que tengan una determinada ciudad
    List<Site> findByCity(String city);

    // Filtrar aquellos sitios que tengan un rate mayor que el que se le pasa como parametro
    @Query("""
            select new com.salesianostriana.dam.dtos.site.GetSiteDto(
                s.id, s.name, s.address, s.city, s.rate, s.scaledFile
            )
            from Site s
            where s.rate >= :rate
            """)
    List<GetSiteDto> findByRateGreaterThan(@Param("rate") Double rate);

    @Query("""
            select new com.salesianostriana.dam.dtos.site.GetSiteDto(
                s.id, s.name, s.address, s.city, s.rate, s.scaledFile
            )
            from Site s
            where s.type >= :type
            """)
    List<GetSiteDto> findByType(@Param("type") SiteTypes type);
}
