package co.edu.umanizales.tads.controller;


import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Ranges;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {


    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private RangeService rangeService;


    @GetMapping
    public ResponseEntity<ResponseDTO> getKids() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listSEService.getKids().getHead(), null), HttpStatus.OK);
    }

    //Este es el controller del primer metodo
    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert() throws ListSEException {
        try {
            if (listSEService.getKids() != null) {
                listSEService.invert();
                return new ResponseEntity<>(new ResponseDTO(200,
                        "La lista se ha invertido", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Este es el controller del segundo metodo
    @GetMapping(path = "/orderboystostart")
    public ResponseEntity<ResponseDTO> orderBoysToStart() throws ListSEException {
        try {
            if (listSEService.getKids() != null) {
                listSEService.getKids().orderBoysToStart();
                return new ResponseEntity<>(new ResponseDTO(200,
                        "Se ha ordenado la lista con los niños al comienzo", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

// Este es el controller del tercer metodo

    @GetMapping(path = "/alternate")
    public ResponseEntity<ResponseDTO> alternateKids() throws ListSEException {
        try {
            if (listSEService.getKids() != null) {
                listSEService.getKids().alternateKids();
                return new ResponseEntity<>(new ResponseDTO(200,
                        "Se ha alternado la lista con niño y niña", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

// Este es el controller del cuarto metodo

    @GetMapping(path = "/deletebyage/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age) throws ListSEException {
        try {
            if (listSEService.getKids() != null) {
                if (age < 0 || age > 14) {
                    throw new ListSEException("La edad debe estar entre 0 y 14 años");
                }
                listSEService.getKids().deleteByAge(age);
                return new ResponseEntity<>(new ResponseDTO(200,
                        "Niños por la edad dada eliminados", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Este es el controller del quinto metodo
    @GetMapping(path = "/averageage")
    public ResponseEntity<ResponseDTO> averageAge() throws ListSEException {
        try {
            if (listSEService.getKids() != null) {
                double averageAge = listSEService.getKids().getAverageByAge();
                return new ResponseEntity<>(new ResponseDTO(
                        200, "El promedio de la edad es aproximadamente: " + averageAge, null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Este es el controller del sexto metodo
    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation() throws ListSEException {
        try {
            List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();

            if (listSEService.getKids() != null) {
                for (Location loc : locationService.getLocations()) {
                    int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
                    if (count > 0) {
                        kidsByLocationDTOList.add(new KidsByLocationDTO(loc, count));
                    }
                }
                return new ResponseEntity<>(new ResponseDTO(200,
                        kidsByLocationDTOList, null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Este es el controller del septimo metodo
    @GetMapping(path = "/winposition/{id}/{numposition}")
    public ResponseEntity<ResponseDTO> winPosition(@PathVariable String id,
                                                   @PathVariable int numposition) throws ListSEException {
        try {
            if (listSEService.getKids() != null) {
                listSEService.getKids().winPosition(id, numposition, listSEService.getKids());
                return new ResponseEntity<>(new ResponseDTO(
                        200, "El niño se ha movido con éxito",
                        null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Este es el controller del octavo metodo
    @GetMapping(path = "/loseposition/{id}/{numposition}")
    public ResponseEntity<ResponseDTO> losePosition(@PathVariable String id,
                                                    @PathVariable int numposition) throws ListSEException {
        try {

            if (listSEService.getKids() != null) {
                listSEService.getKids().losePosition(id, numposition, listSEService.getKids());
                return new ResponseEntity<>(new ResponseDTO(
                        200, "El niño se ha movido con éxito",
                        null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

// Este es el controller del noveno metodo

    @GetMapping(path = "/rangebyage")
    public ResponseEntity<ResponseDTO> getRangeByAge() throws ListSEException {
        try {
            List<RangeDTO> kidsRangeList = new ArrayList<>();

            if (listSEService.getKids() != null) {
                for (Ranges i : rangeService.getRanges()) {
                    int quantity = listSEService.getKids().getRangeByAge(i.getFrom(), i.getTo());
                    kidsRangeList.add(new RangeDTO(i, quantity));
                }
                return new ResponseEntity<>(new ResponseDTO(200, kidsRangeList, null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409, "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}




// Este es el controller del decimo metodo

    @GetMapping(path = "/sendkidend/{letter}")
    public ResponseEntity<ResponseDTO> sendKidFinalByLetter(@PathVariable char letter) throws ListSEException {
        try {
            if (listSEService.getKids() != null) {
                listSEService.getKids().sendKidToTheEndByLetter(letter);
                return new ResponseEntity<>(new ResponseDTO(200,
                        "Se han enviado los niños con la letra dada al final :)", null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


// Hasta aqui van los controller de los 10 metodos de la lista simplemente enlazada



    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() throws ListSEException {
        try {
            if (listSEService.getKids() != null) {
                listSEService.getKids().changeExtremes();
                return new ResponseEntity<>(new ResponseDTO(200,
                        "Se han intercambiado los extremos", null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if(location == null){
            return new ResponseEntity<>(new ResponseDTO(
                    404,"La ubicación no existe",
                    null), HttpStatus.OK);
        }
        try {
            listSEService.getKids().add(
                    new Kid(kidDTO.getIdentification(),
                            kidDTO.getName(), kidDTO.getAge(),
                            kidDTO.getGender(), location));
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha adicionado el petacón",
                null), HttpStatus.OK);

    }


    @GetMapping(path = "/kidsbydepto")
    public ResponseEntity<ResponseDTO> getKidsByDeptoCode() {
        try {
            List<KidsByLocationDTO> kidsByLocationDTOList1 = new ArrayList<>();

            if (listSEService.getKids() != null) {
                for (Location loc : locationService.getLocations()) {
                    int count = listSEService.getKids().getCountKidsByDeptoCode(loc.getCode());
                    if (count > 0) {
                        kidsByLocationDTOList1.add(new KidsByLocationDTO(loc, count));
                    }
                }
                return new ResponseEntity<>(new ResponseDTO(200, kidsByLocationDTOList1,
                        null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKidsLocationGenders(@PathVariable byte age) {
        try {
            if (listSEService.getKids() != null) {
                ReportKidsLocationGenderDTO report = new ReportKidsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
                listSEService.getKids().getReportKidsByLocationGendersByAge(age, report);
                return new ResponseEntity<>(new ResponseDTO(200, report, null), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción", null), HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
