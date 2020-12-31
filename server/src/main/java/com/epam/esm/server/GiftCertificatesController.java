package com.epam.esm.server;

import com.epam.esm.object.Certificate;
import com.epam.esm.service.GiftCertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GiftCertificatesController {

    private GiftCertificatesService giftCertificatesService;

    @Autowired
    public GiftCertificatesController(GiftCertificatesService giftCertificatesService) {
        this.giftCertificatesService = giftCertificatesService;
    }

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "Hello this is a test page";
    }

    @GetMapping("/certificates")
    @ResponseBody
    public List<Certificate> certificates() {
        return giftCertificatesService.allCertificates();
    }
}
