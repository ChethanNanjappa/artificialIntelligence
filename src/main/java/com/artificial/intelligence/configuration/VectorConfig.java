package com.artificial.intelligence.configuration;


import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Configuration
public class VectorConfig {

    @Value("classpath:/RAG_Example_text.txt")
    private Resource textFile;

    @Value("classpath:/Rag_Example_pdf.pdf")
    private Resource pdfFile;

    @Value("classpath:/RAG_Example_image.jpg")
    private Resource imageFile;

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        List<Document> documents = new ArrayList<>();

        HashMap<String, File> vectorStoreFileList = new HashMap<>();
        vectorStoreFileList.put("text", new File("C:\\Practice_Project\\intelligence\\src\\main\\resources\\vectorstore_text.json"));
        vectorStoreFileList.put("pdf", new File("C:\\Practice_Project\\intelligence\\src\\main\\resources\\vectorstore_pdf.json"));
        vectorStoreFileList.put("image", new File("C:\\Practice_Project\\intelligence\\src\\main\\resources\\vectorstore_image.json"));


        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(textFile);
        resourceList.add(pdfFile);
        resourceList.add(imageFile);


        List<DocumentReader> fileReaderList = new ArrayList<>();

        fileReaderList.add(new TextReader(textFile));
        fileReaderList.add(new TextReader(pdfFile));
        fileReaderList.add(new TextReader(imageFile));

        for (Resource resource : resourceList) {
            if (null != resource && null != resource.getFilename() && resource.getFilename().contains(".txt")) {
                if (vectorStoreFileList.get("text").exists()) {
                    simpleVectorStore.load(vectorStoreFileList.get("text"));
                } else {
                    List<Document> textDocument = processTextFile();
                    documents.addAll(textDocument);
                    simpleVectorStore.add(textDocument);
                    simpleVectorStore.save(vectorStoreFileList.get("text"));
                }
            } else if (null != resource && null != resource.getFilename() && resource.getFilename().contains(".pdf")) {
                if (vectorStoreFileList.get("pdf").exists()) {
                    simpleVectorStore.load(vectorStoreFileList.get("pdf"));
                } else {
                    List<Document> pdfDocument = processPdfFile();
                    documents.addAll(pdfDocument);
                    simpleVectorStore.add(pdfDocument);
                    simpleVectorStore.save(vectorStoreFileList.get("pdf"));
                }
            } else if (null != resource && null != resource.getFilename() && resource.getFilename().contains(".jpg")) {
                if (vectorStoreFileList.get("image").exists()) {
                    simpleVectorStore.load(vectorStoreFileList.get("image"));
                } else {
                    List<Document> imageDocument = processJpgFile();
                    documents.addAll(imageDocument);
                    simpleVectorStore.add(imageDocument);
                    simpleVectorStore.save(vectorStoreFileList.get("image"));
                }
            }

        }

       /* if (vectorStoreFile.exists()) {
            simpleVectorStore.load(vectorStoreFile);
        } else {


            TextReader textReader = new TextReader(textFile);
            textReader.getCustomMetadata().put("filename", "RAG_Example.txt");


            List<Document> documents = textReader.get();
            TextSplitter textSplitter = new TokenTextSplitter();
            List<Document> splitDocuments = textSplitter.apply(documents);
            simpleVectorStore.add(documents);
            simpleVectorStore.save(vectorStoreFile);
        }*/
        return simpleVectorStore;
    }

    private List<Document> processTextFile() {
        TextReader textReader = new TextReader(textFile);
        textReader.getCustomMetadata().put("filename", "RAG_Example.txt");

        // Step 1: Read raw documents
        List<Document> rawDocs = textReader.get();

        // ✅ Step 2: Apply chunking (TokenTextSplitter by default)
        TextSplitter splitter = new TokenTextSplitter(); // (chunkSize, overlap)
        return splitter.apply(rawDocs);  // returns smaller document chunks
    }


    public List<Document> processPdfFile() {
        // Step 1: Read the PDF as a list of Documents (1 per page)
        PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfFile);
        List<Document> rawDocuments = reader.get();

        // Step 2: Split documents into smaller chunks using a TokenTextSplitter
        TextSplitter splitter = new TokenTextSplitter(); // 300 tokens per chunk, 50-token overlap
        return splitter.apply(rawDocuments);
    }

    private List<Document> processJpgFile() {
        TikaDocumentReader reader = new TikaDocumentReader(imageFile);
        return reader.get();
    }

}
