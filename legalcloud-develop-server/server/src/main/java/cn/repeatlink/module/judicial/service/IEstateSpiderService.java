/**
 * 
 */
package cn.repeatlink.module.judicial.service;

import java.io.File;
import java.io.InputStream;

import cn.repeatlink.module.judicial.service.impl.EstateSpiderServiceImpl.EsateCaseInfo;
import cn.repeatlink.module.judicial.vo.estate.EstateBaseVo;
import cn.repeatlink.module.judicial.vo.estate.EstateSpiderResultVo;

/**
 * @author LAI
 * @date 2020-12-30 10:33
 */
public interface IEstateSpiderService {

	/**
	 * @param baseVo
	 * @return
	 */
	EstateSpiderResultVo fetch(EstateBaseVo baseVo);

	/**
	 * @param pdfFile
	 * @return
	 */
	EstateSpiderResultVo parseEstatePdf(File pdfFile);

	/**
	 * @param pdfInputStream
	 * @return
	 */
	EstateSpiderResultVo parseEstatePdf(InputStream pdfInputStream);

	/**
	 * @return
	 */
	String getPdfStoreRootFolder();

	/**
	 * @param baseVo
	 * @return
	 */
	EsateCaseInfo buildInfo(EstateBaseVo baseVo);

}
