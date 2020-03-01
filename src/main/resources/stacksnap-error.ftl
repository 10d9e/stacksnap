<#escape x as x?html>
<!DOCTYPE html>
<html>
<head>
    <title>${basic_type}</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#CC2222">
    <meta name="apple-mobile-web-app-status-bar-style" content="#CC2222">
    <style type="text/css"><#include "/public/sparkdebugtools/css/sparkdebugtools_main.css"></style>
    <style type="text/css"><#include "/public/sparkdebugtools/css/sparkdebugtools_code.css"></style>
    <style type="text/css"><#include "/public/sparkdebugtools/css/sparkdebugtools_exception-buttons.css"></style>
</head>
<body>
<div id="spark-header">
    <h1>StackSnap</h1>
    
</div>
<div class="container">
    <div class="stack-container">
        <div class="left-panel cf <#if !frames?has_content>empty</#if>">
            <header>
                <div class="exception">
                    <div class="exc-title">
                        <#list name as namePart><span>${namePart}</span></#list>
                    </div>
                    <p class="exc-message">
                        <#if message == "">No Exception Message<#else>${message}</#if>
                    </p>
                </div>
            </header>
            <div id="exc-btns">
                <button id="copy-button" class="clipboard" data-clipboard-text="${plain_exception}" title="Copy exception details to clipboard">
                    Copy stacktrace
                </button>
                <button id="google-button" data-google-query="<#list name as namePart>${namePart}<#if !namePart?is_last>+</#if></#list>" title="Google this exception">
                    Google exception
                </button>
            </div>
            <div class="frames-description">Stack frames (${frames?size}):</div>
            <div class="frames-container">
                <#assign found = false>
                <#list frames as frame>
                    <div class="frame<#if frame.code??> has-code<#if !found> active<#assign found = true></#if></#if>" id="frame-line-${frame?index}">
                        <div class="frame-method-info">
                            <span class="frame-index">${frames?size - frame?counter}</span>
                            <span class="frame-class">${frame.class}</span>
                            <span class="frame-function">${frame.function}</span>
                        </div>
                        <span class="frame-file">${frame.file}<#if frame.line != "-1"><span class="frame-line">${frame.line}</span></#if></span>
                    </div>
                </#list>
            </div>
        </div>
        <div class="details-container cf">
            <div class="frame-code-container <#if !frames?has_content>empty</#if>">
                <#assign found = false>
                <#list frames as frame>
                    <div class="frame-code<#if frame.code??> has-code<#if !found> active<#assign found = true></#if></#if>" id="frame-code-${frame?index}">
                        <div class="frame-file">
                            <strong>${frame.file}</strong>
                            <#if frame.canonical_path??>
                                (${frame.canonical_path})
                            </#if>
                        </div>
                        <#if frame.code??>
                            <pre class="code-block prettyprint linenums:${frame.code_start}"><code class="language-java">${frame.code}</code></pre>
                        </#if>
                        <div class="frame-comments <#if !frame.comments?has_content>empty</#if>">
                        
                        <#--<#list 0..frames[i].comments?size-1 as commentNo>
                          <#assign comment = frames[i].comments[commentNo]>
                          <div class="frame-comment" id="comment-${i}-${commentNo}">
                            <span class="frame-comment-context">${comment.context}</span>
                            ${comment.text}
                          </div>
                        </#list>-->
                        </div>
                        
                        <div class="details-context">
                    	<#assign data = frame.this>
                        <div class="context-data-table">
                            <h3>Context</h3>
                            <#if data?has_content>
                                <table>
                                   
                                    <#list data?keys as k>
                                        <tr>
                                            <td><div>${k}</div></td>
                                            <td><div>${data[k]}</div></td>
                                        </tr>
                                    </#list>
                                </table>
                            <#else>
                                <span class="empty">EMPTY</span>
                            </#if>
                        </div>
                    </div>
                        
                    </div>
                    
                    
                </#list>
            </div>
            
            <div class="details">
                <div class="data-table-container" id="data-tables">
                    <#list tables?keys as label>
                        <#assign data = tables[label]>
                        <div class="data-table">
                            <h3>${label}</h3>
                            <#if data?has_content>
                                <table class="data-table">
                                    
                                    <#list data?keys as k>
                                        <tr>
                                            <td><div>${k}</div></td>
                                            <td><div>${data[k]}</div></td>
                                        </tr>
                                    </#list>
                                </table>
                            <#else>
                                <span class="empty">EMPTY</span>
                            </#if>
                        </div>
                    </#list>
                </div>
                <div class="data-table-container" id="handlers">
                  <label>Registered Handlers</label>
                    <div class="handler active">
                      1. SomeHandler
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="spark-footer">
    The Stacksnap Debug Screen
</div>
<script><#include "/public/sparkdebugtools/js/lib/sparkdebugtools_zepto.min.js"></script>
<script><#include "/public/sparkdebugtools/js/lib/sparkdebugtools_prettify.min.js"></script>
<script><#include "/public/sparkdebugtools/js/lib/sparkdebugtools_clipboard.min.js"></script>
<script><#include "/public/sparkdebugtools/js/sparkdebugtools_shrink-stackframes.js"></script>
<script><#include "/public/sparkdebugtools/js/sparkdebugtools_main.js"></script>
<noscript><style>.frames-container{display:block}</style></noscript
</body>
</html>
</#escape>
