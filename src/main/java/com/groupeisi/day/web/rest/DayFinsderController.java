package com.groupeisi.day.web.rest;

import com.groupeisi.ws.service.HistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services/calendar")
public class DayFinderController {

    @Autowired
    private HistoriqueService historiqueService;

    @GetMapping("/dayfinder")
    public ResponseEntity<Map<String, String>> findDayOfWeek(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String dayOfWeekString = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.FRENCH);
        Map<String, String> response = new HashMap<>();
        response.put("date", date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        response.put("dayOfWeek", dayOfWeekString);

        historiqueService.addToHistorique(date, dayOfWeekString);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/historique/all")
    public ResponseEntity<List<HistoriqueItem>> getHistorique() {
        List<HistoriqueItem> historique = historiqueService.getAllHistorique();
        return ResponseEntity.ok(historique);
    }
}
