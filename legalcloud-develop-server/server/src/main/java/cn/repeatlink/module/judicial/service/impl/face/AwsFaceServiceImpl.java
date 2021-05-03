/**
 * 
 */
package cn.repeatlink.module.judicial.service.impl.face;

import java.nio.ByteBuffer;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.CreateCollectionRequest;
import com.amazonaws.services.rekognition.model.CreateCollectionResult;
import com.amazonaws.services.rekognition.model.DeleteFacesRequest;
import com.amazonaws.services.rekognition.model.DeleteFacesResult;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.FaceRecord;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.amazonaws.services.rekognition.model.QualityFilter;
import com.amazonaws.services.rekognition.model.ResourceNotFoundException;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import com.amazonaws.services.rekognition.model.UnindexedFace;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.repeatlink.module.judicial.common.Define;
import cn.repeatlink.module.judicial.common.Define.ConfigKeys;
import cn.repeatlink.module.judicial.common.JudicialRuntimeException;
import cn.repeatlink.module.judicial.service.IFaceService;
import cn.repeatlink.module.judicial.vo.face.FaceInfo;
import lombok.extern.log4j.Log4j2;

/**
 * @author LAI
 * @date 2020-09-23 11:47
 * AWS人脸识别服务类
 */
@Log4j2
@Service("awsFaceService")
public class AwsFaceServiceImpl implements IFaceService {
	
	// 2021-03-25
	// 设置默认请求超时时间
	private int timeOut = 1000 * 60;
	private AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().withClientConfiguration(
				new ClientConfiguration().withClientExecutionTimeout(timeOut).withRequestTimeout(timeOut)
			).build();
	
	private Float configMinConfidence() {
		Record record = Db.findFirstByCache(Define.CACHE_NAME_SYS_CONFIG, ConfigKeys.AWS_FACE_CONFIDENCE_MIN, "select * from sys_config where config_key=?", ConfigKeys.AWS_FACE_CONFIDENCE_MIN);
		if(record != null) {
			return Float.valueOf(record.getStr("config_value"));
		}
		return 99.0F;
	}
	
	private String configCollectionId() {
		Record record = Db.findFirstByCache(Define.CACHE_NAME_SYS_CONFIG, ConfigKeys.AWS_FACE_COLLECTION_ID, "select * from sys_config where config_key=?", ConfigKeys.AWS_FACE_COLLECTION_ID);
		if(record != null) {
			return record.getStr("config_value");
		}
		return null;
	}
	
	@Override
	public Float detectFace(String base64data) {
		DetectFacesRequest request = new DetectFacesRequest()
			.withImage(new Image()
				.withBytes(ByteBuffer.wrap(cn.hutool.core.codec.Base64.decode(base64data)))
			)
			.withAttributes(Attribute.DEFAULT);
		try {
			DetectFacesResult result = rekognitionClient.detectFaces(request);
			List<FaceDetail> list = result.getFaceDetails();
			if(list == null || list.isEmpty()) {
				// 未检测出人脸
				throw JudicialRuntimeException.build("judicial.face.detect.not.found");
			}
			if(list.size() > 1) {
				// 检测出人脸多个
				throw JudicialRuntimeException.build("judicial.face.detect.multi.faces");
			}
			FaceDetail faceDetail = list.get(0);
			Float confidence = faceDetail.getConfidence();
			if(confidence < this.configMinConfidence()) {
				// 置信度不够
				throw JudicialRuntimeException.build("judicial.face.detect.not.requirements");
			}
			return confidence;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw JudicialRuntimeException.build("judicial.face.detect.error");
		}
		
	}
	
	@Override
	public FaceInfo saveFaceData(String externalImageId, String base64data) {
		
		// 
		Image image = new Image()
                .withBytes(ByteBuffer.wrap(cn.hutool.core.codec.Base64.decode(base64data)));
        IndexFacesRequest request = new IndexFacesRequest()
                .withImage(image)
                .withQualityFilter(QualityFilter.AUTO)
                .withMaxFaces(1)
                .withCollectionId(this.configCollectionId())
                .withExternalImageId(externalImageId)
                .withDetectionAttributes("DEFAULT");
        
        try {
        	IndexFacesResult result = rekognitionClient.indexFaces(request);
        	List<FaceRecord> faceRecords = result.getFaceRecords();
        	List<UnindexedFace> unindexedFaces = result.getUnindexedFaces();
        	if(unindexedFaces != null && unindexedFaces.size() > 0) {
        		// 
        	}
        	if(faceRecords == null || faceRecords.isEmpty()) {
        		// 
        	}
        	FaceRecord faceRecord = faceRecords.get(0);
        	String faceId = faceRecord.getFace().getFaceId();
        	FaceDetail faceDetail = faceRecord.getFaceDetail();
        	
        	FaceInfo info = new FaceInfo();
        	info.setCollectionId(request.getCollectionId());
        	info.setExternalImageId(request.getExternalImageId());
        	info.setFaceId(faceId);
        	return info;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			// throw JudicialRuntimeException.build("judicial.face.detect.error");
			this.folk(e);
		}
		
		
		return null;
	}
	
	@Override
	public List<String> deleteFaceData(String...faceIds) {
		DeleteFacesRequest deleteFacesRequest = new DeleteFacesRequest()
	              .withCollectionId(this.configCollectionId())
	              .withFaceIds(faceIds);
		try {
			DeleteFacesResult result = rekognitionClient.deleteFaces(deleteFacesRequest);
			return result.getDeletedFaces();
		} catch (Exception e) {
			// 
			log.error(e.getMessage(), e);
			this.folk(e);
		}
		
		return null;
	}
	
	@Override
	public String searchFaceByImage(String base64data) {
		Image image=new Image()
	              .withBytes(ByteBuffer.wrap(cn.hutool.core.codec.Base64.decode(base64data)));
	    SearchFacesByImageRequest request = new SearchFacesByImageRequest()
	              .withCollectionId(this.configCollectionId())
	              .withImage(image)
	              .withFaceMatchThreshold(this.configMinConfidence())
	              .withMaxFaces(1);
	    try {
	    	SearchFacesByImageResult result = rekognitionClient.searchFacesByImage(request);
	    	List<FaceMatch> matches = result.getFaceMatches();
	    	if(matches != null && matches.size() > 0) {
	    		return matches.get(0).getFace().getFaceId();
	    	}
		} catch (Exception e) {
			// 
			log.error(e.getMessage(), e);
			this.folk(e);
		}
	    return null;
	}
	
	@Override
	public List<FaceMatch> searchFacesByImage(String base64data) {
		Image image=new Image()
	              .withBytes(ByteBuffer.wrap(cn.hutool.core.codec.Base64.decode(base64data)));
	    SearchFacesByImageRequest request = new SearchFacesByImageRequest()
	              .withCollectionId(this.configCollectionId())
	              .withImage(image)
	              .withFaceMatchThreshold(this.configMinConfidence())
	              ;
	    try {
	    	SearchFacesByImageResult result = rekognitionClient.searchFacesByImage(request);
	    	List<FaceMatch> matches = result.getFaceMatches();
	    	return matches;
		} catch (Exception e) {
			// 
			log.error(e.getMessage(), e);
			this.folk(e);
		}
	    return null;
	}
	
	private boolean folk(Exception e) {
		if(e instanceof ResourceNotFoundException) {
			if(e.getMessage().startsWith("The collection id")) {
				this.createCollection(this.configCollectionId());
				return true;
			}
		}
		return false;
	}

	private void createCollection(String collectionId) {
		CreateCollectionRequest request = new CreateCollectionRequest()
                .withCollectionId(collectionId);
		CreateCollectionResult createCollectionResult = rekognitionClient.createCollection(request);
	}
}
