package com.monkey1024.gmall.gmallsearchservice.service;

import bean.PmsSearchParam;
import bean.PmsSearchSkuInfo;
import bean.PmsSkuAttrValue;
import com.alibaba.dubbo.config.annotation.Service;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import service.PmsSearchParamService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService implements PmsSearchParamService{

    @Autowired
    JestClient jestClient;
    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam) throws IOException {
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String keyword = pmsSearchParam.getKeyword();
        String[] valueId = pmsSearchParam.getValueId();
        //复杂查询语句
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();

       if (StringUtils.isNotBlank(catalog3Id)){
           TermQueryBuilder termQueryBuilder=new TermQueryBuilder("catalog3Id",catalog3Id);
           boolQueryBuilder.filter(termQueryBuilder);
       }
       if(valueId!=null&&valueId.length!=0)
       {
            for (String pmsSkuAttrValue:valueId){
                TermQueryBuilder termQueryBuilder=new TermQueryBuilder("skuAttrValueList.valueId",pmsSkuAttrValue);
                boolQueryBuilder.filter(termQueryBuilder);
            }
       }
//       if(StringUtils.isNotBlank(keyword)){
//           MatchQueryBuilder matchQueryBuilder=new MatchQueryBuilder("skuName",keyword);
//           boolQueryBuilder.must(matchQueryBuilder);
//           HighlightBuilder highlightBuilder=new HighlightBuilder();
//           highlightBuilder.preTags("<span style='color:red;'>");
//           highlightBuilder.field("skuName");
//           highlightBuilder.postTags("</span>");
//           searchSourceBuilder.highlighter(highlightBuilder);
//       }
       searchSourceBuilder.query(boolQueryBuilder);

        searchSourceBuilder.from(0);
        // size
        searchSourceBuilder.size(20);

        //复杂查询语句dslStr生成完成
        String dslStr=searchSourceBuilder.toString();
        System.out.println(dslStr);

        System.out.println(dslStr);
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
        Search search;
        if (catalog3Id!=null||valueId!=null) {
            search = new Search.Builder(dslStr).addIndex("gmall0105").addType("PmsSkuInfo").build();
        }else
        {
            search=new Search.Builder("{\n" +
                    "  \"query\": {\n" +
                    "    \"bool\": {\n" +
                    "      \"must\": [\n" +
                    "        {\"match\": {\n" +
                    "          \"skuName\": \""+keyword+"\"\n" +
                    "        }\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  }\n" +
                    "  , \"highlight\": {\n" +
                    "     \"pre_tags\": [\n" +
                    "          \"<span style='color:red;'>\"\n" +
                    "      ],\n" +
                    "      \"post_tags\": [\n" +
                    "        \"</span>\"\n" +
                    "      ],\n" +
                    "    \"fields\": {\n" +
                    "      \"skuName\": {}\n" +
                    "    }\n" +
                    "  }\n" +
                    "}").addIndex("gmall0105").addType("PmsSkuInfo").build();
        }
        SearchResult execute = null;


            execute = jestClient.execute(search);
            System.out.println(execute.toString());

        if (execute.isSucceeded()) {
            List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);
            for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
                PmsSearchSkuInfo source = hit.source;
                if (StringUtils.isNotBlank(keyword)) {
                    Map<String, List<String>> highlight = hit.highlight;
                    source.setSkuName(highlight.get("skuName").get(0));
                    // source.setSkuDesc(highlight.get("skuDesc").get(0));
                }
                pmsSearchSkuInfos.add(source);
            }

        }
        return pmsSearchSkuInfos;
    }
}
