package com.increff.assure.service;

import com.increff.assure.dao.OrderDao;
import com.increff.assure.pojo.OrderPojo;
import com.increff.commons.data.InvoiceData;
import com.increff.commons.enums.OrderStatus;
import org.apache.fop.apps.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    private final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());

    public void add(OrderPojo orderPojo) {
        orderDao.add(orderPojo);
    }

    public OrderPojo getOrder(long orderId) throws ApiException {
        OrderPojo orderPojo=orderDao.getOrder(orderId);
        if(orderPojo==null)
            throw new ApiException("order does not exists for given ID");
        return orderPojo;
    }

    public void updateOrderStatus(long orderId, OrderStatus orderStatus) throws ApiException {
        OrderPojo orderPojo=getOrder(orderId);
        orderPojo.setStatus(orderStatus);
    }

    public List<OrderPojo> getAll() {
        return orderDao.getAll();
    }

    public void generateInvoice(InvoiceData invoiceData,long orderId) throws IOException, TransformerException {
        String invoice="main/resources/invoice/Invoice"+orderId+".pdf";
        String xml = javaObjectToXml(invoiceData);
        File xsltFile = new File("src", "main/resources/com.increff.assure/invoice.xml");
        File pdfFile = new File("src", invoice);
        convertToPDF(xsltFile, pdfFile, xml);
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
