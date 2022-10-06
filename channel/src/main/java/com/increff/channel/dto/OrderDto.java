package com.increff.channel.dto;

import com.increff.channel.client.AssureClient;
import com.increff.commons.data.InvoiceData;
import com.increff.commons.data.OrderData;
import com.increff.commons.form.ChannelOrderUploadForm;
import org.apache.fop.apps.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

@Repository
public class OrderDto {

    @Autowired
    private AssureClient assureClient;

    private final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());

    public String add(ChannelOrderUploadForm channelOrderUploadForm) throws Exception {
        return assureClient.post("/order/channel",channelOrderUploadForm);
    }

    public List<OrderData> getAll() {
        //todo call assure api to fetch all
        return null;
    }

    public String generateInvoice(InvoiceData invoiceData) throws IOException, TransformerException {
        String invoice="main/webapp/invoice/Invoice.pdf";
        String xml = javaObjectToXml(invoiceData);
        File xsltFile = new File("src", "main/resources/com.increff.channel/invoice.xml");
        File pdfFile = new File("src", invoice);
        convertToPDF(xsltFile, pdfFile, xml);
        File pdf=new File(invoice);
        return "http://localhost:9000/toyassure/invoice/Invoice.pdf";
    }
    private static String javaObjectToXml(InvoiceData invoiceData) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(InvoiceData.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter stringWriter = new StringWriter();
            jaxbMarshaller.marshal(invoiceData, stringWriter);
            return stringWriter.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return "";
    }
    private void convertToPDF(File xslt, File pdf, String xml)
            throws IOException, TransformerException {
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream out = Files.newOutputStream(pdf.toPath());
        out = new java.io.BufferedOutputStream(out);
        try {
            Fop fop = null;
            try {
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            } catch (FOPException e) {
                throw new RuntimeException(e);
            }
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslt));
            Source src = new StreamSource(new StringReader(xml));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        } catch (FOPException e) {
            throw new RuntimeException(e);
        } finally {
            out.close();
        }
    }
}
